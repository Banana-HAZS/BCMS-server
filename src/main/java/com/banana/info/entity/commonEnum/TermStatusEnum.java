package com.banana.info.entity.commonEnum;

public enum TermStatusEnum {
    /**
     * 待还款
     */
    WAIT_REPAY(1),

    /**
     * 当期已结清
     */
    TERM_PAYOFF(2),

    /**
     * 提前结清
     */
    EARLY_PAYOFF(3),

    /**
     * 已延期
     */
    DELAYED(4),

    /**
     * 已逾期
     */
    OVERDUE(5);


    TermStatusEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
