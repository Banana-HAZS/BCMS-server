package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SysConfig {
    DAYS_OF_MONTH(30L,"一个月按30天计算"),
    MIN_DAYS_OF_TERM(15L,"每个还款周期的最小天数"),
    OVERDUE_AFFECT_DAYS(10L,"逾期天数小于等于10天，则不影响下一期还款日期"),
    OVERDUE_GRACE_PERIOD(7L,"贷款逾期设定7天宽限期"),
    REMIND_INTERVAL(3L,"距离上次催收间隔3天发送一次催收提醒"),
    System(0L,"System","系统Id和名称"),
    ;

    private Long v;
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

    public Long getV() {
        return v;
    }

    public String getInstruction() {
        return instruction;
    }
}
