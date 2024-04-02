package com.banana.common;

public enum BusinessExceptionEnum {
    SYSTEM_BUSY(20000, "系统繁忙，请稍后再试！"),
    ACCOUNTDUPLICATION(20001, "账号重复，请重新输入！"),
    ACCOUNT_NOT_EXIST(20002,"用户名不存在！"),
    INCORRECT_USERNAME_OR_PASSWORD(20003,"用户名或密码错误!"),
    LOGIN_EXPIRED(20004,"登录已过期，请重新登录")
    ;

    private Integer code;

    private String message;

    BusinessExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
