package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public enum OverdueDurationTypeEnum {
    /**
     * 普通逾期
     */
    ORDINARY_OVERDUE(1, 0, 1 * 30),

    /**
     * 较长逾期
     */
    LONG_OVERDUE(2, 1 * 30, 6 * 30),

    /**
     * 严重逾期
     */
    SERIOUS_OVERDUE(3, 6 * 30, Integer.MAX_VALUE);


    OverdueDurationTypeEnum(Integer v) {
        this.v = v;
    }

    private Integer v;

    private Integer down;

    private Integer up;

    public OverdueDurationTypeEnum getOverdueDurationType(Integer overdueDays) {
        OverdueDurationTypeEnum overdueDurationTypeEnum = Arrays.stream(OverdueDurationTypeEnum.values())
                .filter(e -> e.getDown() <= overdueDays && overdueDays < e.getUp())
                .findFirst()
                .get();
        return overdueDurationTypeEnum;
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
}
