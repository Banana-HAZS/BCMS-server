package com.banana.common;

import com.banana.info.entity.commonEnum.SysConfig;

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
    LOAN_SETTLED(20010,"贷款已结清"),
    AMOUNT_EXCEEDS_LIMIT(20011,"贷款金额超出当前额度上限"),
    IDCARD_IS_EMPTY(20012,"请输入身份证号码"),
    DELAY_STATUS_INVALID(20013,"当前状态不允许展期"),
    DELAY_NUM_REACHED_LOAN_MAX(20014,"本次贷款展期次数已达上限"),
    DELAY_NUM_REACHED_LOAN_RECOVER_MAX(20015,"当期还款展期次数已达上限"),
    DELAY_TERMS_EXCEEDS_LIMIT(20016,String.format("展期期数超过上限，每次展期的最大期限是%d期", SysConfig.DELAY_MAX_LIMIT.getV())),
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
