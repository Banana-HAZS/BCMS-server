package com.banana.info.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
public class LoanApplyParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 申请日期
     */
    private List<LocalDateTime> applyDate;

    /**
     * 申请开始日期
     */
    private LocalDateTime applyStartDate;

    /**
     * 申请结束日期
     */
    private LocalDateTime applyEndDate;

    /**
     * 审核状态(1待审核、2审核中、3已通过、4已驳回)
     */
    private Integer auditType;

    /**
     * 贷款状态(1等待审核、2待放款、3放款中、4已放款、5已驳回、6已结清)
     */
    private Integer loanStatus;

    /**
     * 还款方式(1等额本息还款、2等额本金还款、3按期付息、4分期还款)
     */
    private Integer repayMethod;

    /**
     * 客户联系方式
     */
    private String customerPhone;

    public void dateTimeInit() {
        if (Objects.nonNull(applyDate) && applyDate.size() > 0) {
            applyStartDate = applyDate.get(0);
            applyEndDate = applyDate.get(1);
        }
    }
}
