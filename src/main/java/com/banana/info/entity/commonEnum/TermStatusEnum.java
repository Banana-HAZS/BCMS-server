package com.banana.info.entity.commonEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private static Map<Integer,TermStatusEnum> VEnumMap;

    static {
        VEnumMap = Arrays.stream(TermStatusEnum.values())
                .collect(Collectors.toMap(TermStatusEnum::getV, Function.identity()));
    }

    public static TermStatusEnum getEnumByV(Integer v){
        return VEnumMap.get(v);
    }

    public Integer getV() {
        return v;
    }
}
