package com.banana.info.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
@Data
public class OverdueRecordsSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户账号
     */
    private String customerAccount;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 贷款id
     */
    private Integer loanId;

    /**
     * 贷款收回id
     */
    private Integer loanRecoverId;

    /**
     * 逾期金额
     */
    private BigDecimal overduePrice;

    /**
     * 逾期时长类型(1普通逾期、2较长逾期、3严重逾期)
     */
    private Integer overdueDurationType;

    /**
     * 逾期开始日期
     */
    private LocalDateTime overdueStartDate;

    /**
     * 逾期结束日期
     */
    private LocalDateTime overdueEndDate;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 逾期罚息
     */
    private BigDecimal lateCharge;

    /**
     * 提醒状态(1暂无、2催收提醒、3已提醒)
     */
    private Integer remindStatus;

    /**
     * 下一次提醒时间
     */
    private LocalDateTime nextRemindTime;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 客户联系方式
     */
    private String customerPhone;

}
