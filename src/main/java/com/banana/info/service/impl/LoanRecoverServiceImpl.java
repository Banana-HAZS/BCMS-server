package com.banana.info.service.impl;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.info.entity.*;
import com.banana.info.entity.commonEnum.*;
import com.banana.info.entity.param.LoanRecoverDelayPayoffParam;
import com.banana.info.entity.param.LoanRecoverEarlyPayoffParam;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.vo.InitDelayFormVO;
import com.banana.info.entity.vo.LoanRecoverSearchVO;
import com.banana.info.mapper.*;
import com.banana.info.service.ICustomerCreditService;
import com.banana.info.service.ILoanRecoverService;
import com.banana.info.service.IOverdueRecordsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.TODO;
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
    private CustomerCreditServiceImpl customerCreditService;

    @Resource
    private IOverdueRecordsService overdueRecordsService;

    @Resource
    private OverdueRecordsMapper overdueRecordsMapper;

    @Resource
    private CreditScoreRecordsServiceImpl creditScoreRecordsService;

    @Resource
    private DelayRecordsMapper delayRecordsMapper;

    @Override
    public Map<String, Object> getLoanRecoverList(LoanRecoverSearchParam param) {

        // 时间初始化
        param.dateTimeInit();

        // 更新贷款收回记录
        updateLoanRecover();

        Page<LoanRecoverSearchVO> page = loanRecoverMapper
                .getLoanRecoverPage(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    /**
     * 更新贷款收回记录
     */
    public void updateLoanRecover() {
        List<LoanRecover> loanRecovers = loanRecoverMapper.selectList(null);

        // 更新已逾期记录，和贷款收回的逾期罚息
        overdueRecordsService.updateOverdueRecords();
        // 获取待还款的贷款收回
        List<LoanRecover> WaitRepayLoanRecovers = loanRecovers.stream()
                .filter(lr -> lr.getTermStatus().equals(TermStatusEnum.WAIT_REPAY.getV()))
                .collect(Collectors.toList());
        // 本期逾期的贷款收回id
        List<Integer> overdueRecoverIds = WaitRepayLoanRecovers.stream()
                .filter(lr -> lr.getRepayDate().isBefore(LocalDateTime.now()))
                .map(lr -> lr.getId())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(overdueRecoverIds)) {
            return;
        }
        // 更新状态
        loanRecoverMapper.update(null,
                new LambdaUpdateWrapper<LoanRecover>()
                        .in(LoanRecover::getId, overdueRecoverIds)
                        .set(LoanRecover::getTermStatus, TermStatusEnum.OVERDUE.getV()));

        // 批量创建贷款逾期记录
        overdueRecordsService.batchAddOverdueRecords(overdueRecoverIds);
    }

    @Override
    public void addLoanRecover(Loan loan) {
        LoanRecover loanRecover = loan.createLoanRecover();

        interestCompute(loan, loanRecover);

        // 按还款方式赋值
        if (loan.getRepayMethod() == RepayMethodEnum.EQUAL_PRICE.getV()) {
            loanRecover.setTermRepayPrice(loan.getTermRepayPrice());
            loanRecover.setTermRepayPrincipal(loanRecover.getTermRepayPrice()
                    .subtract(loanRecover.getTermRepayInterest()));
            loan.setTermRepayPrincipal(loanRecover.getTermRepayPrincipal());
        } else if (loan.getRepayMethod() == RepayMethodEnum.EQUAL_PRINCIPAL.getV()) {
            loanRecover.setTermRepayPrincipal(loan.getTermRepayPrincipal());
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                    .add(loanRecover.getTermRepayInterest()));
            loan.setTermRepayPrice(loanRecover.getTermRepayPrice());
        } else if (loan.getRepayMethod() == RepayMethodEnum.INTEREST_REGULARLY.getV()) {
            BigDecimal principal = loan.getCurrentTerm().equals(loan.getRepayTerm()) ?
                    loan.getPrice() : BigDecimal.ZERO;
            loanRecover.setTermRepayPrincipal(principal);
            loanRecover.setTermRepayPrice(loanRecover.getTermRepayInterest()
                    .add(loanRecover.getTermRepayPrincipal()));
            loan.setTermRepayPrincipal(loanRecover.getTermRepayPrincipal());
            loan.setTermRepayPrice(loanRecover.getTermRepayPrice());
        }

        // 设置待还金额
        loanRecover.setRemainRepayPrice(loanRecover.getTermRepayPrice());

        loanMapper.updateById(loan);
        loanRecoverMapper.insert(loanRecover);
    }

    private void interestCompute(Loan loan, LoanRecover loanRecover) {
        long days;
        // 日利率
        BigDecimal dayInterestRate = loanRecover.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);
        // 情况一：第一期利率计算
        if (loan.getCurrentTerm().equals(1)) {
            // 按放款日期再计算一次还款日期，每一期要大于等于15天，否则向后顺延一月
            days = Duration.between(loan.getGrantDate(), loan.getRepayDate()).toDays();
            if (days >= SysConfig.MIN_DAYS_OF_TERM.getV()) {
                loanRecover.setRepayDate(loan.getRepayDate());
            } else {
                loanRecover.setRepayDate(loan.getRepayDate().plusMonths(1));
                // 更新Loan
                loan.setRepayDate(loan.getRepayDate().plusMonths(1));
                loan.setLoanDate(loan.getLoanDate().plusMonths(1));
            }
            // 获取更新后的第一期天数
            days = Duration.between(loan.getGrantDate(), loanRecover.getRepayDate())
                    .toDays();
            // 第一期利息按 (还款日期-放款日期)天数 * 日利率
            loanRecover.setTermRepayInterest(loanRecover.getBalance()
                    .multiply(dayInterestRate)
                    .multiply(BigDecimal.valueOf(days)));
            loan.setTermRepayInterest(loanRecover.getTermRepayInterest());
            return;
        }

        // 获取当前贷款下最新一期贷款收回
        LoanRecover lastLoanRecover = getLastLoanRecover(loan.getId());

        // 情况二：最新一期还款存在逾期
        if (lastLoanRecover.getActualRepayDate().isAfter(lastLoanRecover.getRepayDate())) {
            // 贷款逾期天数小于等于10天，则不影响下一期还款日期，大于10天，则下一期还款日期顺延一月。
            Long overdueDays = Duration.between(lastLoanRecover.getActualRepayDate(),
                            lastLoanRecover.getRepayDate())
                    .toDays();
            if (overdueDays <= SysConfig.OVERDUE_AFFECT_DAYS.getV()) {
                loanRecover.setRepayDate(lastLoanRecover.getRepayDate().plusMonths(1));
            } else {
                loanRecover.setRepayDate(lastLoanRecover.getRepayDate().plusMonths(2));
                // 更新Loan
                loan.setRepayDate(loanRecover.getRepayDate());
                loan.setLoanDate(loan.getLoanDate().plusMonths(1));
            }
            // 获取更新后的当期天数
            days = Duration.between(lastLoanRecover.getActualRepayDate(),
                            loanRecover.getRepayDate())
                    .toDays();
            // 计算利息 (当期还款期限-上一期实际结清日期)天数 * 日利率
            loanRecover.setTermRepayInterest(loanRecover.getBalance()
                    .multiply(dayInterestRate)
                    .multiply(BigDecimal.valueOf(days)));
            loan.setTermRepayInterest(loanRecover.getTermRepayInterest());
            return;
        }

        // 情况三：最近一期没有逾期(延期是以“期”为单位，不影响计算) days = 30
        loanRecover.setRepayDate(lastLoanRecover.getRepayDate().plusMonths(1));
        // 更新Loan
        loan.setRepayDate(loanRecover.getRepayDate());

        loanRecover.setTermRepayInterest(loanRecover.getBalance()
                .multiply(dayInterestRate)
                .multiply(BigDecimal.valueOf(SysConfig.DAYS_OF_MONTH.getV())));
        loan.setTermRepayInterest(loanRecover.getTermRepayInterest());
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
        repayRecordsMapper.insert(repayRecord);

        BigDecimal actualRepayPrice = loanRecover.getActualRepayPrice()
                .add(param.getRepayPrice());
        BigDecimal remainRepayPrice = loanRecover.getRemainRepayPrice()
                .subtract(param.getRepayPrice());

        loanRecover.setActualRepayPrice(actualRepayPrice);
        loanRecover.setRemainRepayPrice(remainRepayPrice);
        loanRecoverMapper.updateById(loanRecover);

        // 如果贷款已结清，则更新贷款收回、总贷款属性，同时更新客户信用分，并创建下一期还款。
        if (remainRepayPrice.compareTo(BigDecimal.ZERO) == 0) {
            // 根据结清类型，更新客户信用分
            updateCreditScore(loanRecover);

            // 有逾期的需要同步更新逾期结束日期
            OverdueRecords overdueRecords = overdueRecordsService
                    .getOverdueRecordsByLoanRecover(loanRecover.getId());
            if (ObjectUtils.isNotEmpty(overdueRecords)) {
                overdueRecords.setOverdueEndDate(LocalDateTime.now());
                overdueRecordsMapper.updateById(overdueRecords);
            }

            // 更新贷款收回状态
            loanRecover.setTermStatus(TermStatusEnum.TERM_PAYOFF.getV());
            loanRecover.setActualRepayDate(LocalDateTime.now());

            Loan loan = loanMapper.selectById(loanRecover.getLoanId());
            // 剩余本金和已收回利息在每一期还款结清时更新
            loan.setBalance(loan.getBalance()
                    .subtract(loanRecover.getTermRepayPrincipal()));
            loan.setRecoveredInterest(loan.getRecoveredInterest()
                    .add(loanRecover.getTermRepayInterest()));

            // 判断总贷款是否已结清
            if (loan.getCurrentTerm().equals(loan.getRepayTerm())) {
                loan.setLoanStatus(LoanStatusEnum.SETTLED.getV());
                // 一次计入信用分的贷款需满足贷款金额不小于5000，贷款周期大于6个月
                // 若提前结清则计算实际贷款周期
                if (loan.getPrice().compareTo(
                        BigDecimal.valueOf(SysConfig.CREDIT_LOAN_PRICE_DOWN.getV())) >= 0 &&
                        loan.getCurrentTerm() >= SysConfig.CREDIT_LOAN_TERM_DOWN.getV()
                ) {
                    creditScoreRecordsService.addCreditScoreRecords(loanRecover,
                            CreditScoreChangeTypeEnum.FULL_LOAN_SETTLE);
                    customerCreditService.updateCustomerCreditScore(loanRecover.getCustomerId(),
                            CreditScoreChangeTypeEnum.FULL_LOAN_SETTLE);
                }
            }
            // 这里需要更新一下数据库，避免下面创建下一期还款时获取到过时信息
            loanRecoverMapper.updateById(loanRecover);
            // 未结清，创建下一期还款
            if (!loan.getLoanStatus().equals(LoanStatusEnum.SETTLED.getV())) {
                loan.setCurrentTerm(loan.getCurrentTerm() + 1);
                addLoanRecover(loan);
            }
            loanMapper.updateById(loan);
        }
    }

    private void updateCreditScore(LoanRecover loanRecover) {
        switch (TermStatusEnum.getEnumByV(loanRecover.getTermStatus())) {
            case DELAYED: {
                // 创建延期还款信用分变更记录
                creditScoreRecordsService.addCreditScoreRecords(loanRecover,
                        CreditScoreChangeTypeEnum.DELAYED);
                customerCreditService.updateCustomerCreditScore(loanRecover.getCustomerId(),
                        CreditScoreChangeTypeEnum.DELAYED);
                break;
            }
            case OVERDUE: {
                // 创建逾期还款信用分变更记录
                OverdueRecords overdueRecords = overdueRecordsMapper.selectOne(
                        new LambdaQueryWrapper<OverdueRecords>()
                                .eq(OverdueRecords::getLoanRecoverId, loanRecover.getId()));
                // 获取逾期时长类型
                OverdueDurationTypeEnum overdueDurationType = OverdueDurationTypeEnum
                        .getEnumByV(overdueRecords.getOverdueDurationType());
                creditScoreRecordsService.addCreditScoreRecords(loanRecover,
                        overdueDurationType.toCreditScoreChangeTypeEnum());
                customerCreditService.updateCustomerCreditScore(loanRecover.getCustomerId(),
                        overdueDurationType.toCreditScoreChangeTypeEnum());
                break;
            }
            case WAIT_REPAY: {
                // 创建正常还款信用分变更记录
                creditScoreRecordsService.addCreditScoreRecords(loanRecover,
                        CreditScoreChangeTypeEnum.TERM_LOAN_PAYOFF);
                customerCreditService.updateCustomerCreditScore(loanRecover.getCustomerId(),
                        CreditScoreChangeTypeEnum.TERM_LOAN_PAYOFF);
            }
        }
    }

    @Override
    public void earlyPayoff(LoanRecoverEarlyPayoffParam param) {
        LoanRecover loanRecover = loanRecoverMapper.selectById(param.getId());
        Loan loan = loanMapper.selectById(loanRecover.getLoanId());
        // 贷款已结清不执行
        if (Objects.equals(loan.getLoanStatus(), LoanStatusEnum.SETTLED.getV())) {
            throw new BusinessException(BusinessExceptionEnum.LOAN_SETTLED);
        }

        // 只运行当期贷款的最新一期还款执行提前结清
        Integer lastTerm = getLoanRecoverListByLoanId(loanRecover.getLoanId()).stream()
                .map(LoanRecover::getCurrentTerm)
                .max(Integer::compareTo)
                .orElse(null);
        if (!loanRecover.getCurrentTerm().equals(lastTerm)) {
            throw new BusinessException(BusinessExceptionEnum.NOT_LAST_TERM);
        }

        // 如果当期已结清，提前结清金额等于剩余本金
        if (Objects.equals(loanRecover.getTermStatus(), TermStatusEnum.TERM_PAYOFF.getV())) {
            // createRepayRecord()中已经计算了累计还款金额，所以要在loanRecover.setActualRepayPrice()之前，避免重复计算
            RepayRecords repayRecord = loanRecover.createRepayRecord(loan.getBalance());

            loanRecover.setActualRepayPrice(loanRecover.getActualRepayPrice()
                    .add(repayRecord.getRepayPrice()));
            loanRecover.setTermStatus(TermStatusEnum.EARLY_PAYOFF.getV());

            repayRecordsMapper.insert(repayRecord);
        } else { // 当期未结清，提前结清金额等于剩余本金+当期利息-当期累计已还款
            RepayRecords repayRecord = loanRecover.createRepayRecord(loan.getBalance()
                    .add(loanRecover.getTermRepayInterest())
                    .subtract(loanRecover.getActualRepayPrice()));

            loanRecover.setActualRepayDate(LocalDateTime.now());
            loanRecover.setActualRepayPrice(loanRecover.getActualRepayPrice()
                    .add(repayRecord.getRepayPrice()));
            loanRecover.setRemainRepayPrice(BigDecimal.ZERO);

            loan.setRecoveredInterest(loan.getRecoveredInterest()
                    .add(loanRecover.getTermRepayInterest()));

            repayRecordsMapper.insert(repayRecord);
        }
        loanRecover.setTermStatus(TermStatusEnum.EARLY_PAYOFF.getV());
        loan.setBalance(BigDecimal.ZERO);
        loan.setLoanStatus(LoanStatusEnum.SETTLED.getV());
        loanRecoverMapper.updateById(loanRecover);
        loanMapper.updateById(loan);
    }

    @Override
    public void delayPayoff(LoanRecoverDelayPayoffParam param) {
        LoanRecover loanRecover = loanRecoverMapper.selectById(param.getId());
        Loan loan = loanMapper.selectById(loanRecover.getLoanId());

        // 只有待还款状态下允许展期
        if (!loanRecover.getTermStatus().equals(TermStatusEnum.WAIT_REPAY.getV())) {
            throw new BusinessException(BusinessExceptionEnum.DELAY_STATUS_INVALID);
        }

        // 超过延期允许次数则驳回
        if (Objects.equals(loan.getDelayNum(), loan.getDelayMaxNum())) {
            throw new BusinessException(BusinessExceptionEnum.DELAY_NUM_REACHED_LOAN_MAX);
        }
        if (Objects.equals(loanRecover.getDelayNum(), SysConfig.LOAN_RECOVER_DEALY_VALID_NUM.getV().intValue())) {
            throw new BusinessException(BusinessExceptionEnum.DELAY_NUM_REACHED_LOAN_RECOVER_MAX);
        }
        // 超过延期允许的最大期限则驳回
        if (param.getDelayTerms() > SysConfig.DELAY_MAX_LIMIT.getV()){
            throw new BusinessException(BusinessExceptionEnum.DELAY_TERMS_EXCEEDS_LIMIT);
        }

        // 1. 新建延期还款记录
        DelayRecords delayRecords = new DelayRecords();
        delayRecords.setLoanId(loanRecover.getLoanId());
        delayRecords.setLoanRecoverId(loanRecover.getId());
        delayRecords.setCustomerId(loanRecover.getCustomerId());
        delayRecords.setLoanNo(loanRecover.getLoanNo());
        delayRecords.setLoanRecoverNo(loanRecover.getLoanRecoverNo());
        delayRecords.setDelayPrice(param.getDelayPrice());
        delayRecords.setDelayStartDate(param.getDelayStartDate());
        delayRecords.setDelayEndDate(param.getDelayEndDate());
        delayRecords.setDelayTerms(param.getDelayTerms());
        delayRecords.setDelayChargeBase(param.getDelayChargeBase());
        delayRecords.setDelayCharge(param.getDelayCharge());
        delayRecords.setDelayInterestAdjust(param.getDelayInterestAdjust());
        delayRecords.setRemindStatus(RemindStatusEnum.CURRENT_NOT.getV());
        delayRecords.setNextRemindTime(delayRecords.getDelayEndDate().plusDays(-2L));
        delayRecordsMapper.insert(delayRecords);

        // 2. 更新loanRecover
        // 展期期间利率调整，不影响调整前的利息
        loanRecover.setInterestRate(loanRecover.getInterestRate().add(param.getDelayInterestAdjust()));
        loanRecover.setRepayDate(param.getDelayEndDate());
        // 展期期间月利率
        BigDecimal monthInterestRate = loanRecover.getInterestRate()
                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        // 展期期间利息
        BigDecimal delayTermInterest = loanRecover.getBalance()
                .multiply(monthInterestRate);
        loanRecover.setTermRepayInterest(loanRecover.getTermRepayInterest().add(delayTermInterest));
        loanRecover.setTermRepayPrice(loanRecover.getTermRepayPrincipal()
                .add(loanRecover.getTermRepayInterest()));
        loanRecover.setRemainRepayPrice(loanRecover.getTermRepayPrice()
                .subtract(loanRecover.getActualRepayPrice()));
        loanRecover.setTermStatus(TermStatusEnum.DELAYED.getV());
        loanRecover.setDelayTerm(param.getDelayTerms());
        loanRecover.setInterestRateAdjust(param.getDelayInterestAdjust());
        loanRecover.setDelayNum(loanRecover.getDelayNum() + 1);
        loanRecoverMapper.updateById(loanRecover);

        // 3. 更新loan
        loan.setRepayDate(loanRecover.getRepayDate());
        loan.setTermRepayInterest(loanRecover.getTermRepayInterest());
        loan.setTermRepayPrice(loanRecover.getTermRepayPrice());
        loan.setLoanDate(loan.getLoanDate().plusMonths(param.getDelayTerms()));
        loan.setDelayNum(loan.getDelayNum() + 1);
        loanMapper.updateById(loan);
    }

    @Override
    public InitDelayFormVO initDelayForm() {
        InitDelayFormVO initDelayFormVO = new InitDelayFormVO();
        initDelayFormVO.setDelayChargeBase(SysConfig.DELAY_CHARGE_BASE.getBv());
        initDelayFormVO.setDelayInterestAdjust(SysConfig.DELAY_INTEREST_ADJUST.getBv());
        return initDelayFormVO;
    }

    /*
    剩余本金1000  当期本200 利100 总300
    --已结清--
    剩余本金：800
    当期累计还款：800(提前结清)+300(当期结清)=1100
    --未结清--   当期还款为0时，提前结清：1000(剩余本金)+100(当期利息)=1100
    剩余本金：1000
    假设已还款：250    (200本 50利)
    当期累计还款：
        提前结清：1000(剩余本金)+100(当期利息)-250
        当期已还：250
        总计：1000(剩余本金)+100(当期利息)-250+250=1100

    总结：当期累计还款相等
     */
    public List<LoanRecover> getLoanRecoverListByLoanId(Integer loanId) {
        return loanRecoverMapper.selectList(new LambdaQueryWrapper<LoanRecover>()
                .eq(LoanRecover::getLoanId, loanId));
    }

    /**
     * 获取当前贷款下最新一期贷款收回
     */
    private LoanRecover getLastLoanRecover(Integer loanId) {
        return loanRecoverMapper.selectList(new LambdaQueryWrapper<LoanRecover>()
                .eq(LoanRecover::getLoanId, loanId)
                .orderByDesc(LoanRecover::getCurrentTerm)
                .last("limit 1")
        ).get(0);
    }
}
