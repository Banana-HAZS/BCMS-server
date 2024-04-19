package com.banana.info.entity.commonEnum;

public enum LoanStatusEnum {
    /**
     * 等待审核
     */
    WAIT_AUDIT(1),

    /**
     * 待放款
     */
    WAIT_GRANT(2),

    /**
     * 放款中
     */
    GRANTING(3),

    /**
     * 已放款
     */
    GRANTED(4),

    /**
     * 已驳回
     */
    REJECTED(5),

    /**
     * 已结清
     */
    SETTLED(6);


    LoanStatusEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
