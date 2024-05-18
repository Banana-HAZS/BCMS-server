package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public enum LoanLimitEnum {
    //    S(100亿元)、A+(10亿元)、A(1亿元)、A-(1千万元)、B(1百万元)、C+(50万元)、C(10万元)、D(1万元)、E(5千元)、F(2千元)、G(0元)
    S("S", "S类客户", new BigDecimal("10000000000")),
    A_PLUS("A+", "A+类客户", new BigDecimal("1000000000")),
    A("A", "A类客户", new BigDecimal("100000000")),
    A_MINUS("A-", "A-类客户", new BigDecimal("10000000")),
    B("B", "B类客户", new BigDecimal("1000000")),
    C_PLUS("C+", "C+类客户", new BigDecimal("500000")),
    C("C", "C类客户", new BigDecimal("100000")),
    D("D", "D类客户", new BigDecimal("10000")),
    E("E", "E类客户", new BigDecimal("5000")),
    F_PLUS("F+", "F+类客户", new BigDecimal("3000")),
    F("F", "F类客户", new BigDecimal("2000")),
    G("G", "G类客户", new BigDecimal("0"));

    private String code;
    private String name;
    private BigDecimal limit;
    private static Map<String, LoanLimitEnum> codeEnumMap;

    static {
        codeEnumMap = Arrays.stream(LoanLimitEnum.values())
                .collect(Collectors.toMap(LoanLimitEnum::getCode, Function.identity()));
    }

    public static LoanLimitEnum getEnumByCode(String code) {
        return codeEnumMap.get(code);
    }



    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLimit() {
        return limit;
    }
}
