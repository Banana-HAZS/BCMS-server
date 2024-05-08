package com.banana.info.entity.param;

import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.commonEnum.EvaluateStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EvaluateCustomerLoanLimitParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 贷款额度等级S(100亿元)、A+(10亿元)、A(1亿元)、A-(1千万元)、B(1百万元)、C+(50万元)、C(10万元)、D(1万元)、E(5千元)、F(2千元)、G(0元)',
     */
    private String loanLimitLevel;
}
