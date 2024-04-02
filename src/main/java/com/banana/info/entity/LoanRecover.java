package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 贷款收回表
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Data
@TableName("loan_recover")
public class LoanRecover implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 贷款id
     */
    private Integer loanId;

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
     * 当期期还款金额(本金+利息+罚息)
     */
    private BigDecimal termRepayPrice;

    /**
     * 逾期罚息
     */
    private BigDecimal lateCharge;

    /**
     * 实际还款金额
     */
    private BigDecimal actualRepayPrice;

    /**
     * 实际还款日期
     */
    private LocalDateTime actualRepayDate;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 剩余期数
     */
    private Integer remainTerm;

    /**
     * 余额
     */
    private BigDecimal balance;

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
