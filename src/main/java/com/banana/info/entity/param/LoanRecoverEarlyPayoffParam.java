package com.banana.info.entity.param;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoanRecoverEarlyPayoffParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款收回id
     */
    private Integer id;

}
