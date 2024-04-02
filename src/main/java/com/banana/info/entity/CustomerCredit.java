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
 * 客户信用表
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Data
@TableName("customer_credit")
public class CustomerCredit implements Serializable {

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
     * 贷款次数
     */
    private Integer loanNum;

    /**
     * 贷款总金额
     */
    private BigDecimal loanTotalPrice;

    /**
     * 逾期还款次数
     */
    private Integer lateNum;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 年收入(元)
     */
    private BigDecimal annualIncome;

    /**
     * 学历
     */
    private String educational;

    /**
     * 负债金额(元)
     */
    private BigDecimal debtAmount;

    /**
     * 房产(平)
     */
    private Integer houseProperty;

    /**
     * 车产(辆)
     */
    private Integer carProperty;

    /**
     * 储蓄(元)
     */
    private BigDecimal deposit;

    /**
     * 信用评估等级(1A、2B、3C、4D、5E)
     */
    private Integer creditLevel;

    /**
     * 初次评估日期
     */
    private LocalDateTime evaluateDate;

    /**
     * 评估人id
     */
    private Integer evaluatorId;

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
