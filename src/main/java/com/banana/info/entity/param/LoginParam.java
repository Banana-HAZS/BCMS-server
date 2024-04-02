package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
}
