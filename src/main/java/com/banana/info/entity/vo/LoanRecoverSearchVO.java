package com.banana.info.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class LoanRecoverSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 贷款id
     */
    private Integer loanId;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 贷款金额
     */
    private BigDecimal price;

    /**
     * 当期利率
     */
    private BigDecimal interestRate;

    /**
     * 当期还款日期
     */
    private LocalDateTime repayDate;

    /**
     * 当期还款本金
     */
    private BigDecimal termRepayPrincipal;

    /**
     * 当期还款利息
     */
    private BigDecimal termRepayInterest;

    /**
     * 当期还款金额(本金+利息)
     */
    private BigDecimal termRepayPrice;

    /**
     * 实际还款金额(本金+利息)
     */
    private BigDecimal actualRepayPrice;

    /**
     * 实际还款日期
     */
    private LocalDateTime actualRepayDate;

    /**
     * 还款方式(1等额本息还款、2等额本金还款、3按期付息、4分期还款)
     */
    private Integer repayMethod;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 剩余期数
     */
    private Integer remainTerm;

    /**
     * 逾期罚息基数(130%-150%)
     */
    private BigDecimal lateChargeBase;

    /**
     * 剩余本金
     */
    private BigDecimal balance;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    /**
     * 当期还款状态(1待还款、2当期已结清、3提前结清、4已延期、5已逾期)
     */
    private Integer termStatus;

    /**
     * 延期月数
     */
    private Integer delayNum;

    /**
     * 利率调整
     */
    private BigDecimal interestRateAdjust;

}
