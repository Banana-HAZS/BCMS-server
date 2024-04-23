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
@TableName("repay_records")
public class RepayRecords implements Serializable {

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
     * 还款日期
     */
    private LocalDateTime repayDate;

    /**
     * 还款金额
     */
    private BigDecimal repayPrice;

    /**
     * 实际还款金额
     */
    private BigDecimal actualRepayPrice;

    /**
     * 剩余待还金额
     */
    private BigDecimal remainRepayPrice;

}
