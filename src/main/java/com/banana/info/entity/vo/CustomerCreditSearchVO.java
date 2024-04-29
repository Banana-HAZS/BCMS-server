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
 * @since 2024-04-29
 */
@Data
public class CustomerCreditSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private Integer id;

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
     * 客户联系方式
     */
    private String customerPhone;

    /**
     * 信用分
     */
    private Integer creditScore;

    /**
     * 信用评估等级A(700-950：信用极好)、B(650-700：信用优秀)、C(600-650：信用良好)、D(550-600：信用中等)、E(350-550：信用较差)
     */
    private String creditLevel;
}
