package com.banana.info.entity.param;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RepayRecordsSearchParam extends PageParam implements Serializable {

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
     * 当期还款日期
     */
    private List<LocalDateTime> repayDate;

    /**
     * 当期还款开始日期
     */
    private LocalDateTime repayStartDate;

    /**
     * 当期还款结束日期
     */
    private LocalDateTime repayEndDate;

    /**
     * 当期还款状态(1待还款、2当期已结清、3提前结清、4已延期、5已逾期)
     */
    private Integer termStatus;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    public void dateTimeInit() {
        if (CollectionUtils.isNotEmpty(repayDate)) {
            repayStartDate = repayDate.get(0);
            repayEndDate = repayDate.get(1);
        }
    }
}
