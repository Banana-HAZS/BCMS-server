package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
public class LoanRecoverRepayParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款收回id
     */
    private Integer id;

    /**
     * 还款金额
     */
    private BigDecimal repayPrice;

}
