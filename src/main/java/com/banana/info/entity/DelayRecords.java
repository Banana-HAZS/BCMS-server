package com.banana.info.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 延期记录表
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DelayRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 贷款id
     */
    private Integer loanId;

    /**
     * 贷款收回id
     */
    private Integer loanRecoverId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

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

}
