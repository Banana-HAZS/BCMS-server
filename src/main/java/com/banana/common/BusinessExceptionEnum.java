package com.banana.common;

public enum BusinessExceptionEnum {
    SYSTEM_BUSY(20000, "系统繁忙，请稍后再试！"),
    ACCOUNTDUPLICATION(20001, "账号重复，请重新输入！"),
    ACCOUNT_NOT_EXIST(20002,"用户名不存在！"),
    INCORRECT_USERNAME_OR_PASSWORD(20003,"用户名或密码错误!"),
    LOGIN_EXPIRED(20004,"登录已过期，请重新登录"),
    IDCARD_NOT_EXIST(20005,"用户信息不存在！请检查您输入的身份证号码"),
    AUDIT_TYPE_ERROR(20006,"仅可审核待审核的申请"),
    GTANT_TYPE_ERROR(20007,"仅可操作待放款的单据"),
    OVERPAY(20008,"超额还款，还款金额不能超过剩余待还金额"),
    NOT_LAST_TERM(20009,"只允许在最新一期还款上执行提前结清"),
    REPEATED_EARLY_PAYOFF(20010,"已提前结清，不要重复执行"),
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
