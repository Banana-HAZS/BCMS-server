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
     * 信用分
     */
    private Integer creditScore;

    /**
     * 信用评估等级A(700-950：信用极好)、B(650-700：信用优秀)、C(600-650：信用良好)、D(550-600：信用中等)、E(350-550：信用较差)
     */
    private String creditLevel;

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
