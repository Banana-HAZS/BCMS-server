package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmRemindParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款收回id或者逾期记录id
     */
    private Integer id;

}
