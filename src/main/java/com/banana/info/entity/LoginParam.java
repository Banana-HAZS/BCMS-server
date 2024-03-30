package com.banana.info.entity;

import java.io.Serializable;

public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private Integer id;

    /**
     * 密码
     */
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
