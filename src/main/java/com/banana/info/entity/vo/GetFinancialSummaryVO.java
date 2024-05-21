package com.banana.info.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2024-05-21
 */
@Data
public class GetFinancialSummaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 利息收入
     */
    private BigDecimal interest;

    /**
     * 手续费及佣金收入
     */
    private BigDecimal fee;

    /**
     * 其他收入
     */
    private BigDecimal other;

    /**
     * 不良贷款率
     */
    private BigDecimal badLoanRatio;
}
