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
public class RepayRecordsSearchVO implements Serializable {

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
     * 还款日期
     */
    private LocalDateTime repayDate;

    /**
     * 还款金额
     */
    private BigDecimal repayPrice;

    /**
     * 累计还款金额
     */
    private BigDecimal actualRepayPrice;

    /**
     * 剩余待还金额
     */
    private BigDecimal remainRepayPrice;

    /**
     * 当前期数
     */
    private Integer currentTerm;

    /**
     * 客户联系方式
     */
    private String customerPhone;

}
