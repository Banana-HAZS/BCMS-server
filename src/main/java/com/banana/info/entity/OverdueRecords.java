package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 贷款收回表
 * </p>
 *
 * @author zjy
 * @since 2024-04-23
 */
@Data
@TableName("overdue_records")
public class OverdueRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 逾期类型(1完全逾期、2部分逾期)
     */
    private Integer overdueType;

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
     * 逾期罚息基数(130%-150%)
     */
    private BigDecimal lateChargeBase;

    /**
     * 提醒状态(1暂无、2催收提醒、3已提醒)
     */
    private Integer remindStatus;

    /**
     * 下一次提醒时间
     */
    private LocalDateTime nextRemindTime;
}
