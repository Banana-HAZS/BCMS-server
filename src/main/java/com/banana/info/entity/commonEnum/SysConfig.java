package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum SysConfig {
    DAYS_OF_MONTH(30L,"一个月按30天计算"),
    MIN_DAYS_OF_TERM(15L,"每个还款周期的最小天数"),
    OVERDUE_AFFECT_DAYS(10L,"逾期天数小于等于10天，则不影响下一期还款日期"),
    OVERDUE_GRACE_PERIOD(7L,"贷款逾期设定7天宽限期"),
    REMIND_INTERVAL(3L,"距离上次催收间隔3天发送一次催收提醒"),
    SYSTEM(0L,"System","系统Id和名称"),
    CREDIT_LOAN_PRICE_DOWN(5000L,"一次计入信用分的完整贷款金额下限"),
    CREDIT_LOAN_TERM_DOWN(6L,"一次计入信用分的完整贷款周期下限"),
    LOAN_DEALY_VALID_NUM(new BigDecimal("0.1"),"一次完整贷款允许的展期次数是贷款期数的10%，向下取整"),
    LOAN_RECOVER_DEALY_VALID_NUM(1L,"每次还款允许的展期次数是1次"),
    DELAY_MAX_LIMIT(2L,"每次展期的最大期限是2期"),
    DELAY_CHARGE_BASE(new BigDecimal("0.005"),"展期手续费是展期金额的0.5%;"),
    DELAY_INTEREST_ADJUST(new BigDecimal("0.005"),"展期期间利率调整，在原基础上增加0.5%;"),
    DONT_REMIND_TIME(LocalDateTime.of(1111, 11, 11, 0, 0, 0),"将不再进行提醒的下一次提醒时间置为此值"),
    ;

    private Long v;
    private BigDecimal bv;
    private LocalDateTime time;
    private String name;
    private String instruction;

    SysConfig(Long v, String instruction) {
        this.v = v;
        this.instruction = instruction;
    }

    SysConfig(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    SysConfig(Long v, String name, String instruction) {
        this.v = v;
        this.name = name;
        this.instruction = instruction;
    }

    SysConfig(BigDecimal bv, String instruction) {
        this.bv = bv;
        this.instruction = instruction;
    }

    SysConfig(LocalDateTime time, String instruction) {
        this.time = time;
        this.instruction = instruction;
    }
}
