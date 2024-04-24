package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.commonEnum.LoanStatusEnum;
import com.banana.info.entity.commonEnum.RepayMethodEnum;
import com.banana.info.entity.commonEnum.TermStatusEnum;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.entity.vo.LoanRecoverSearchVO;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.mapper.LoanRecoverMapper;
import com.banana.info.mapper.RepayRecordsMapper;
import com.banana.info.service.IEmployeeService;
import com.banana.info.service.ILoanRecoverService;
import com.banana.tool.LoanRecoverNoGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 贷款收回表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Service
public class LoanRecoverServiceImpl extends ServiceImpl<LoanRecoverMapper, LoanRecover> implements ILoanRecoverService {

    @Resource
    private LoanMapper loanMapper;

    @Resource
    private LoanRecoverMapper loanRecoverMapper;

    @Resource
    private RepayRecordsMapper repayRecordsMapper;

    @Resource
    private LoanRecoverNoGenerator loanRecoverNoGenerator;

    @Override
    public Map<String, Object> getLoanRecoverList(LoanRecoverSearchParam param) {

        // 时间初始化
        param.dateTimeInit();

        Page<LoanRecoverSearchVO> page = loanRecoverMapper
                .getLoanRecoverPage(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    @Override
    public void addLoanRecover(Loan loan) {
        LoanRecover loanRecover = new LoanRecover();
        loanRecover.setLoanNo(loan.getLoanNo());
        loanRecover.setLoanRecoverNo(loanRecoverNoGenerator.generateUniqueCode());
        loanRecover.setCustomerId(loan.getCustomerId());
        loanRecover.setLoanId(loan.getId());
        loanRecover.setPrice(loan.getPrice());
        loanRecover.setInterestRate(loan.getInterestRate());
        loanRecover.setActualRepayPrice(BigDecimal.ZERO);
        loanRecover.setLateCharge(BigDecimal.ZERO);
        loanRecover.setCurrentTerm(1);
        loanRecover.setRemainTerm(loan.getRepayTerm() - loanRecover.getCurrentTerm());
        loanRecover.setBalance(loan.getPrice());
        loanRecover.setTermStatus(TermStatusEnum.WAIT_REPAY.getV());
        loanRecover.setDelayNum(0);
        loanRecover.setInterestRateAdjust(BigDecimal.ZERO);

        // 按放款日期再计算一次还款日期，每一期要大于等于15天，否则向后顺延一月
        long days = Duration.between(loan.getGrantDate(), loan.getRepayDate()).toDays();
        if (days >= 15L) {
            loanRecover.setRepayDate(loan.getRepayDate());
        } else {
            loanRecover.setRepayDate(loan.getRepayDate().plusMonths(1));
            // 更新Loan
            loan.setRepayDate(loan.getRepayDate().plusMonths(1));
            loan.setLoanDate(loan.getLoanDate().plusMonths(1));
            loanMapper.updateById(loan);
        }
        // 获取更新后的第一期天数
        days = Duration.between(loan.getGrantDate(), loanRecover.getRepayDate()).toDays();
        // 第一期利息按 (还款日期-放款日期)天数 * 日利率
        BigDecimal dayInterestRate = loanRecover.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);

        loanRecover.setTermRepayInterest(loanRecover.getBalance()
                .multiply(dayInterestRate)
                .multiply(BigDecimal.valueOf(days)));

        // 按还款方式赋值
        if (loan.getRepayMethod() == RepayMethodEnum.EQUAL_PRICE.getV()) {
            loanRecover.setTermRepayPrice(loan.getTermRepayPrice());
            loanRecover.setTermRepayPrincipal(loanRecover.getTermRepayPrice()
                    .subtract(loanRecover.getTermRepayInterest()));
        } else if (loan.getRepayMethod() == RepayMethodEnum.EQUAL_PRINCIPAL.getV()) {
            loanRecover.setTermRepayPrincipal(loan.getTermRepayPrincipal());
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                    .add(loanRecover.getTermRepayInterest()));
        } else if (loan.getRepayMethod() == RepayMethodEnum.INTEREST_REGULARLY.getV()) {
            loanRecover.setTermRepayPrincipal(BigDecimal.ZERO);
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayInterest());
        }

        // 设置初始待还金额
        loanRecover.setRemainRepayPrice(loanRecover.getTermRepayPrice());

        loanRecoverMapper.insert(loanRecover);
    }

    @Override
    public void repay(LoanRecoverRepayParam param) {
        LoanRecover loanRecover = loanRecoverMapper.selectById(param.getId());

        // 还款金额大于剩余待还金额，抛出超额还款异常
        if (param.getRepayPrice().compareTo(loanRecover.getRemainRepayPrice()) > 0) {
            throw new BusinessException(BusinessExceptionEnum.OVERPAY);
        }

        //创建还款记录
        RepayRecords repayRecord = loanRecover.createRepayRecord(param.getRepayPrice());

        BigDecimal actualRepayPrice = loanRecover.getActualRepayPrice().add(param.getRepayPrice());
        BigDecimal remainRepayPrice = loanRecover.getRemainRepayPrice().subtract(param.getRepayPrice());
        // 判断是否结清
        if (remainRepayPrice.compareTo(BigDecimal.ZERO) == 0){
            loanRecover.setTermStatus(TermStatusEnum.TERM_PAYOFF.getV());
            loanRecover.setActualRepayDate(LocalDateTime.now());

            Loan loan = loanMapper.selectById(loanRecover.getLoanId());
            loan.setBalance(loan.getBalance().subtract(loanRecover.getTermRepayPrincipal()));
            loan.setRecoveredInterest(loan.getRecoveredInterest().add(loanRecover.getTermRepayInterest()));
            if(loan.getBalance().equals(BigDecimal.ZERO)){
                loan.setLoanStatus(LoanStatusEnum.SETTLED.getV());
            }
            loanMapper.updateById(loan);
        }
        loanRecover.setActualRepayPrice(actualRepayPrice);
        loanRecover.setRemainRepayPrice(remainRepayPrice);

        loanRecoverMapper.updateById(loanRecover);
        repayRecordsMapper.insert(repayRecord);
    }

    @Override
    public void earlyPayoff(LoanRecoverRepayParam param) {
        LoanRecover loanRecover = loanRecoverMapper.selectById(param.getId());

        if(loanRecover.getTermStatus()==TermStatusEnum.EARLY_PAYOFF.getV()){
            throw new BusinessException(BusinessExceptionEnum.REPEATED_EARLY_PAYOFF);
        }

        // 只运行当期贷款的最新一期还款执行提前结清
        Integer lastTerm = getLoanRecoverListByLoanId(loanRecover.getLoanId())
                .stream()
                .map(lr -> lr.getCurrentTerm())
                .max(Integer::compareTo)
                .orElse(null);
        if(!loanRecover.getCurrentTerm().equals(lastTerm)){
            throw new BusinessException(BusinessExceptionEnum.NOT_LAST_TERM);
        }

        Loan loan = loanMapper.selectById(loanRecover.getLoanId());
        // createRepayRecord()中已经计算了累计还款金额，所以要在loanRecover.setActualRepayPrice()之前，避免重复计算
        RepayRecords repayRecord = loanRecover.createRepayRecord(loan.getBalance());
        // 如果当期已结清，提前结清金额等于剩余本金
        if(loanRecover.getTermStatus()==TermStatusEnum.TERM_PAYOFF.getV()){
            loanRecover.setActualRepayPrice(loanRecover.getActualRepayPrice().add(loan.getBalance()));

            loan.setBalance(BigDecimal.ZERO);
            loan.setLoanStatus(LoanStatusEnum.SETTLED.getV());
        }
/*
剩余本金1000  当期本200 利100 总300
--已结清--
剩余本金：800
当期提前结清付出：300+800=1100
--未结清--
剩余本金：1000
假设已还款：250
当期提前结清付出：250+1000+100-250=1100

总结：未结清的先结清吧
 */
        else { // 当期未结清，提前结清金额等于剩余本金+当期利息
            loanRecover.setActualRepayPrice(loanRecover.getActualRepayPrice().add(loan.getBalance()));
            loanRecover.setActualRepayDate(LocalDateTime.now());
        }

        loanRecover.setTermStatus(TermStatusEnum.EARLY_PAYOFF.getV());


    }

    public List<LoanRecover> getLoanRecoverListByLoanId(Integer loanId){
        return loanRecoverMapper.selectList(
                new LambdaQueryWrapper<LoanRecover>()
                        .eq(LoanRecover::getLoanId,loanId)
        );
    }
}
