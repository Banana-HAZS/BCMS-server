package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class getLoanLimitParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    private Integer customerId;

}
