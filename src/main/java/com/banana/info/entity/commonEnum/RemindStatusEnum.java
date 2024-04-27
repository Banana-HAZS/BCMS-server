package com.banana.info.entity.commonEnum;

public enum RemindStatusEnum {
    /**
     * 暂无
     */
    CURRENT_NOT(1),

    /**
     * 催收提醒
     */
    REMIND_RECOVER(2),

    /**
     * 已提醒
     */
    REMINDED(3);


    RemindStatusEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
