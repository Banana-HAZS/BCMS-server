package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class DelayRecordsSearchParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户联系方式
     */
    private String customerPhone;
}
