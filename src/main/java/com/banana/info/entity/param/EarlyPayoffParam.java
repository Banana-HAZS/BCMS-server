package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EarlyPayoffParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款收回id
     */
    private Integer id;

}
