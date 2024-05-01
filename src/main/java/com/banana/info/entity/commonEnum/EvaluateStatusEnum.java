package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EvaluateStatusEnum {
    /**
     * 待评估
     */
    WAIT_EVALUATE(1),

    /**
     * 已评估
     */
    EVALUATED(2);


    private Integer v;

    public Integer getV() {
        return v;
    }
}
