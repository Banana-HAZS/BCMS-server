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
 * @since 2024-03-29
 */
@Data
public class GetLoanLimitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 贷款额度等级S(100亿元)、A+(10亿元)、A(1亿元)、A-(1千万元)、B(1百万元)、C+(50万元)、C(10万元)、D(1万元)、E(5千元)、F(2千元)、G(0元)
     */
    private String loanLimitLevel;

    /**
     * 贷款额度对应称呼
     */
    private String loanLimitLevelName;

    /**
     * 贷款额度上限
     */
    private BigDecimal loanLimit;

    /**
     * 评估状态(1待评估 2已评估)
     */
    private Integer evaluateStatus;

}
