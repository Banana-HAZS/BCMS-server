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
public class DelayRecordsSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

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
     * 贷款id
     */
    private Integer loanId;

    /**
     * 贷款收回id
     */
    private Integer loanRecoverId;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    /**
     * 展期金额
     */
    private BigDecimal delayPrice;

    /**
     * 展期开始日期
     */
    private LocalDateTime delayStartDate;

    /**
     * 展期结束日期
     */
    private LocalDateTime delayEndDate;

    /**
     * 展期期数
     */
    private Integer delayTerms;

    /**
     * 展期手续费
     */
    private BigDecimal delayCharge;

    /**
     * 展期手续费占比(基于展期金额)
     */
    private BigDecimal delayChargeBase;

    /**
     * 展期利率调整(基于原利率)
     */
    private BigDecimal delayInterestAdjust;

    /**
     * 提醒状态(1暂无、2还款提醒、3已提醒)
     */
    private Integer remindStatus;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 下一次提醒时间
     */
    private LocalDateTime nextRemindTime;
}
