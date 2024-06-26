package com.banana.info.entity.param;

import com.banana.info.entity.Loan;
import com.banana.info.entity.commonEnum.SysConfig;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
@Data
public class LoanSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 姓名
     */
    private String customerName;

    /**
     * 银行预留手机号
     */
    private String customerPhone;

    /**
     * 贷款金额
     */
    private BigDecimal price;

    /**
     * 贷款类型(1个人贷款、2商业贷款)
     */
    private Integer loanType;

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
     * 逾期罚息基数(130%-150%)
     */
    private BigDecimal lateChargeBase;

    /**
     * 贷款用途
     */
    private String loanPurpose;

    public Loan toLoan(){
        Loan loan = new Loan();
        loan.setPrice(price);
        loan.setLoanType(loanType);
        loan.setApplyDate(LocalDateTime.now());
        loan.setAuditType(1);
        loan.setLoanStatus(1);
        loan.setInterestRate(interestRate);
        loan.setTermRepayPrincipal(termRepayPrincipal);
        loan.setTermRepayInterest(termRepayInterest);
        loan.setTermRepayPrice(termRepayPrice);
        loan.setRepayDate(repayDate);
        loan.setRepayMethod(repayMethod);
        loan.setRepayTerm(repayTerm);
        loan.setLateChargeBase(lateChargeBase);
        loan.setLoanPurpose(loanPurpose);
        loan.setRecoveredInterest(new BigDecimal(0.0000));
        loan.setBalance(price);
        loan.setDelayMaxNum(SysConfig.LOAN_DEALY_VALID_NUM.getBv()
                .multiply(BigDecimal.valueOf(repayTerm))
                .setScale(0, RoundingMode.DOWN)
                .intValue());

        return loan;
    }
}
