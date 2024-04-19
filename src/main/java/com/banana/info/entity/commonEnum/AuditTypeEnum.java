package com.banana.info.entity.commonEnum;

import lombok.Data;

public enum AuditTypeEnum {
    /**
     * 待审核
     */
    WAIT_AUDIT(1),

    /**
     * 审核中
     */
    AUDITING(2),

    /**
     * 已通过
     */
    PASSED(3),

    /**
     * 已驳回
     */
    REJECTED(4);


    AuditTypeEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    public Integer getV() {
        return v;
    }
}
