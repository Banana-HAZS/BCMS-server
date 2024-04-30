package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public enum CreditScoreChangeTypeEnum {
    /**
     * 一次完整的贷款结清
     */
    FULL_LOAN_SETTLE(1, "一次完整的贷款结清", +25,"full_loan_settle"),

    /**
     * 一期贷款收回结清
     */
    TERM_LOAN_PAYOFF(2, "一期贷款收回结清", +2,"term_loan_payoff"),

    /**
     * 一次延期还款
     */
    DELAYED(3, "一次延期还款", -2,"delayed"),

    /**
     * 一次普通逾期还款
     */
    ORDINARY_OVERDUE(4, "一次普通逾期还款", -5,"ordinary_overdue"),

    /**
     * 一次较长逾期还款
     */
    LONG_OVERDUE(5, "一次较长逾期还款", -10,"long_overdue"),

    /**
     * 一次严重逾期还款
     */
    SERIOUS_OVERDUE(6, "一次严重逾期还款", -20,"serious_overdue"),
    ;


    private Integer code;

    private String description;

    private Integer value;

    // 用来跟OverdueDurationTypeEnum做映射
    private String overdueMap;

    private static Map<Integer,CreditScoreChangeTypeEnum> codeEnumMap;

    private static Map<String,CreditScoreChangeTypeEnum> overdueMapEnumMap;

    // 初始化codeEnumMap，静态代码将在加载类时自动执行
    static {
        // Function.identity() 恒等函数，传入什么就输出什么，用于做转换
        codeEnumMap = Arrays.stream(CreditScoreChangeTypeEnum.values())
                .collect(Collectors.toMap(CreditScoreChangeTypeEnum::getCode, Function.identity()));
        overdueMapEnumMap = Arrays.stream(CreditScoreChangeTypeEnum.values())
                .collect(Collectors.toMap(CreditScoreChangeTypeEnum::getOverdueMap, Function.identity()));
    }

    public static CreditScoreChangeTypeEnum getEnumByCode(Integer code){
        return codeEnumMap.get(code);
    }

    public static CreditScoreChangeTypeEnum getEnumByOverdueMap(String overdueMap){
        return overdueMapEnumMap.get(overdueMap);
    }

    CreditScoreChangeTypeEnum(Integer code, String description, Integer value) {
        this.code = code;
        this.description = description;
        this.value = value;
        this.overdueMap = null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
    }

    public String getOverdueMap() {
        return overdueMap;
    }
}
