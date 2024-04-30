package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public enum OverdueDurationTypeEnum {
    /**
     * 普通逾期
     */
    ORDINARY_OVERDUE(1, 0, 1 * 30, "ordinary_overdue"),

    /**
     * 较长逾期
     */
    LONG_OVERDUE(2, 1 * 30, 6 * 30, "long_overdue"),

    /**
     * 严重逾期
     */
    SERIOUS_OVERDUE(3, 6 * 30, Integer.MAX_VALUE, "serious_overdue");


    OverdueDurationTypeEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    private Integer down;

    private Integer up;

    // 用来跟CreditScoreChangeTypeEnum做映射
    private String creditMap;

    private static Map<Integer, OverdueDurationTypeEnum> vEnumMap;

    static {
        vEnumMap = Arrays.stream(OverdueDurationTypeEnum.values())
                .collect(Collectors.toMap(OverdueDurationTypeEnum::getV, Function.identity()));
    }

    public static OverdueDurationTypeEnum getOverdueDurationType(Integer overdueDays) {
        OverdueDurationTypeEnum overdueDurationTypeEnum = Arrays.stream(OverdueDurationTypeEnum.values())
                .filter(e -> e.getDown() <= overdueDays && overdueDays < e.getUp())
                .findFirst()
                .get();
        return overdueDurationTypeEnum;
    }

    public static OverdueDurationTypeEnum getEnumByV(Integer v){
        return vEnumMap.get(v);
    }

    public CreditScoreChangeTypeEnum toCreditScoreChangeTypeEnum(){
        return CreditScoreChangeTypeEnum.getEnumByOverdueMap(creditMap);
    }

    public Integer getV() {
        return v;
    }

    public Integer getDown() {
        return down;
    }

    public Integer getUp() {
        return up;
    }

    public String getCreditMap() {
        return creditMap;
    }
}
