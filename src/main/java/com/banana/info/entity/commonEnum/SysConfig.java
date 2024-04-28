package com.banana.info.entity.commonEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SysConfig {
    MIN_DAYS_OF_TERM(15L,"每个还款周期的最小天数"),
    OVERDUE_AFFECT_DAYS(10L,"逾期天数小于等于10天，则不影响下一期还款日期"),
    OVERDUE_GRACE_PERIOD(7L,"贷款逾期设定7天宽限期"),
    REMIND_INTERVAL(3L,"距离上次催收间隔3天发送一次催收提醒"),
    ;

    private Long v;
    private String instruction;

    public Long getV() {
        return v;
    }

    public String getInstruction() {
        return instruction;
    }
}
