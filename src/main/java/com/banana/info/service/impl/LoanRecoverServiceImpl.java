package com.banana.info.service.impl;

import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.commonEnum.RepayMethodEnum;
import com.banana.info.entity.commonEnum.TermStatusEnum;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.entity.vo.LoanRecoverSearchVO;
import com.banana.info.mapper.CustomerMapper;
import com.banana.info.mapper.LoanMapper;
import com.banana.info.mapper.LoanRecoverMapper;
import com.banana.info.service.IEmployeeService;
import com.banana.info.service.ILoanRecoverService;
import com.banana.tool.LoanRecoverNoGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        loanRecover.setRepayDate(loan.getRepayDate());
        loanRecover.setLateCharge(BigDecimal.ZERO);
        loanRecover.setCurrentTerm(1);
        loanRecover.setRemainTerm(loan.getRepayTerm() - loanRecover.getCurrentTerm());
        loanRecover.setBalance(loan.getPrice());
        loanRecover.setTermStatus(TermStatusEnum.WAIT_REPAY.getV());
        loanRecover.setDelayNum(0);
        loanRecover.setInterestRateAdjust(BigDecimal.ZERO);

        // 第一期利息按 (还款日期-放款日期)天数 * 日利率
        long days = Duration.between(loan.getGrantDate(), loanRecover.getRepayDate()).toDays();
        BigDecimal dayInterestRate = loanRecover.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);

        loanRecover.setTermRepayInterest(loanRecover.getBalance()
                .multiply(dayInterestRate)
                .multiply(BigDecimal.valueOf(days)));

        // 按还款方式赋值
        if(loan.getRepayMethod()== RepayMethodEnum.EQUAL_PRICE.getV()){
            loanRecover.setTermRepayPrice(loan.getTermRepayPrice());
            loanRecover.setTermRepayPrincipal(loanRecover.getTermRepayPrice()
                    .subtract(loanRecover.getTermRepayInterest()));
        }else if(loan.getRepayMethod()== RepayMethodEnum.EQUAL_PRINCIPAL.getV()){
            loanRecover.setTermRepayPrincipal(loan.getTermRepayPrincipal());
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                    .add(loanRecover.getTermRepayInterest()));
        }else if(loan.getRepayMethod()== RepayMethodEnum.INTEREST_REGULARLY.getV()){
            loanRecover.setTermRepayPrincipal(BigDecimal.ZERO);
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayInterest());
        }

        loanRecoverMapper.insert(loanRecover);
    }
}
