package com.banana.info.entity.commonEnum;

public enum RepayMethodEnum {
    /**
     * 等额本息还款
     */
    EQUAL_PRICE(1),

    /**
     * 等额本金还款
     */
    EQUAL_PRINCIPAL(2),

    /**
     * 按期付息
     */
    INTEREST_REGULARLY(3);


    RepayMethodEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
