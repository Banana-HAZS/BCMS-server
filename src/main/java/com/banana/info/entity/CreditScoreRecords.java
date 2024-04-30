package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 信用分变动记录表
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreditScoreRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 贷款流水号
     */
    private String loanNo;

    /**
     * 贷款收回流水号
     */
    private String loanRecoverNo;

    /**
     * 变动类型
     */
    private Integer changeType;

    /**
     * 变动描述
     */
    private String changeDescription;

    /**
     * 变动值
     */
    private Integer changeValue;

    /**
     * 变动日期
     */
    private LocalDateTime changeDate;


}
