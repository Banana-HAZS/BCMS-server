package com.banana.info.entity;

import com.banana.info.entity.commonEnum.TermStatusEnum;
import com.banana.tool.LoanRecoverNoGenerator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 贷款表
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Data
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 贷款金额
     */
    private BigDecimal price;

    /**
     * 贷款类型(1个人贷款、2商业贷款)
     */
    private Integer loanType;

    /**
     * 贷款申请执行人id
     */
    private Integer applyExecutorId;

    /**
     * 申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 审核状态(1待审核、2审核中、3已通过、4已驳回)
     */
    private Integer auditType;

    /**
     * 审核人id
     */
    private Integer auditorId;

    /**
     * 审核日期
     */
    private LocalDateTime auditDate;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 贷款发放执行人id
     */
    private Integer grantExecutorId;

    /**
     * 贷款发放日期
     */
    private LocalDateTime grantDate;

    /**
     * 贷款状态(1等待审核、2待放款、3放款中、4已放款、5已驳回、6已结清)
     */
    private Integer loanStatus;

    /**
     * 利率
     */
    private BigDecimal interestRate;

    /**
     * 还款日期(每一期的还款日期)
     */
    private LocalDateTime repayDate;

    /**
     * 每期还款本金
     */
    private BigDecimal termRepayPrincipal;

    /**
     * 每期还款利息
     */
    private BigDecimal termRepayInterest;

    /**
     * 每期还款金额(本金+利息)
     */
    private BigDecimal termRepayPrice;

    /**
     * 还款方式(1等额本息还款、2等额本金还款、3按期付息)
     */
    private Integer repayMethod;

    /**
     * 还款期数(月)
     */
    private Integer repayTerm;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 贷款期限(最后的结清日期)
     */
    private LocalDateTime loanDate;

    /**
     * 逾期罚息基数(130%-150%)
     */
    private BigDecimal lateChargeBase;

    /**
     * 贷款用途
     */
    private String loanPurpose;

    /**
     * 已收回利息
     */
    private BigDecimal recoveredInterest;

    /**
     * 剩余本金
     */
    private BigDecimal balance;

    public LoanRecover createLoanRecover() {
        LoanRecover loanRecover = new LoanRecover();
        loanRecover.setLoanNo(loanNo);
        loanRecover.setLoanRecoverNo(LoanRecoverNoGenerator.generateUniqueCode());
        loanRecover.setCustomerId(customerId);
        loanRecover.setLoanId(id);
        loanRecover.setPrice(price);
        loanRecover.setInterestRate(interestRate);
        loanRecover.setActualRepayPrice(BigDecimal.ZERO);
        loanRecover.setLateCharge(BigDecimal.ZERO);
        loanRecover.setCurrentTerm(currentTerm);
        loanRecover.setRemainTerm(repayTerm - loanRecover.getCurrentTerm());
        loanRecover.setBalance(balance);
        loanRecover.setTermStatus(TermStatusEnum.WAIT_REPAY.getV());
        loanRecover.setDelayNum(0);
        loanRecover.setInterestRateAdjust(BigDecimal.ZERO);
        return loanRecover;
    }
}
