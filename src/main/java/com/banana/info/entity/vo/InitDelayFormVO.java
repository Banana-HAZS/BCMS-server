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
 * @since 2024-05-17
 */
@Data
public class InitDelayFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 展期手续费占比(基于展期金额)
     */
    private BigDecimal delayChargeBase;

    /**
     * 展期利率调整(基于原利率)
     */
    private BigDecimal delayInterestAdjust;
}
