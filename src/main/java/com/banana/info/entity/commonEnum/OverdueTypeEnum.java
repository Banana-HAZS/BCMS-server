package com.banana.info.entity.commonEnum;

public enum OverdueTypeEnum {
    /**
     * 完全逾期
     */
    COMPLETE_OVERDUE(1),

    /**
     * 部分逾期
     */
    PART_OVERDUE(2),
    ;


    OverdueTypeEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
