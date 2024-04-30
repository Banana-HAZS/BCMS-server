package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
public class CreditScoreRecordsSearchParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 变动类型
     */
    private Integer changeType;

    /**
     * 变动日期
     */
    private List<LocalDateTime> changeDate;

    /**
     * 变动开始日期
     */
    private LocalDateTime changeStartDate;

    /**
     * 变动结束日期
     */
    private LocalDateTime changeEndDate;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    public void dateTimeInit() {
        if (Objects.nonNull(changeDate) && changeDate.size() > 0) {
            changeStartDate = changeDate.get(0);
            changeEndDate = changeDate.get(1);
        }
    }
}
