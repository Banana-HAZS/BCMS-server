package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanRecoverDelayPayoffParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款收回id
     */
    private Integer id;

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
