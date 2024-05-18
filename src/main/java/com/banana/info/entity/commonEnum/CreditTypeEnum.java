package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public enum CreditTypeEnum {
    // A(700-950：信用极好)、B(650-700：信用优秀)、C(600-650：信用良好)、D(550-600：信用中等)、E(350-550：信用较差)
    excellent("A", "信用极好", 700, 950),
    better("B", "信用优秀", 650, 700),
    well("C", "信用良好", 600, 650),
    medium("D", "信用中等", 550, 600),
    poor("E", "信用较差", 350, 550);

    private static Integer MAX_SCORE = 950;
    private static Integer MIN_SCORE = 350;
    private String code;
    private String name;
    private Integer down;
    private Integer up;

    public static CreditTypeEnum getCreditByScore(Integer score) {
        if (score >= MAX_SCORE) {
            return excellent;
        }
        if (score < MIN_SCORE) {
            return poor;
        }
        CreditTypeEnum creditTypeEnum = Arrays.stream(CreditTypeEnum.values())
                .filter(e -> e.getDown() <= score && score < e.getUp())
                .findFirst()
                .get();
        return creditTypeEnum;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getUp() {
        return up;
    }

    public Integer getDown() {
        return down;
    }
}
