package com.banana.info.entity.vo;

import com.banana.info.entity.Employee;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class LoanApplyVO implements Serializable {

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
     * 贷款金额
     */
    private BigDecimal price;

    /**
     * 贷款类型(1个人贷款、2商业贷款)
     */
    private Integer loanType;

    /**
     * 贷款申请执行人id
     */
    private Integer applyExecutorId;

    /**
     * 贷款申请执行人
     */
    private String applyExecutor;

    /**
     * 申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 审核状态(1待审核、2审核中、3已通过、4已驳回)
     */
    private Integer auditType;

    /**
     * 审核人id
     */
    private Integer auditorId;

    /**
     * 审核人
     */
    private String auditorName;

    /**
     * 审核日期
     */
    private LocalDateTime auditDate;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 贷款发放执行人id
     */
    private Integer grantExecutorId;

    /**
     * 贷款发放执行人
     */
    private String grantExecutor;

    /**
     * 贷款发放日期
     */
    private LocalDateTime grantDate;

    /**
     * 贷款状态(1等待审核、2待放款、3放款中、4已放款、5已驳回、6已结清)
     */
    private Integer loanStatus;

    /**
     * 利率
     */
    private BigDecimal interestRate;

    /**
     * 还款日期(每一期的还款日期)
     */
    private LocalDateTime repayDate;

    /**
     * 每期还款本金
     */
    private BigDecimal termRepayPrincipal;

    /**
     * 每期还款利息
     */
    private BigDecimal termRepayInterest;

    /**
     * 每期还款金额(本金+利息)
     */
    private BigDecimal termRepayPrice;

    /**
     * 还款方式(1等额本息还款、2等额本金还款、3按期付息、4分期还款)
     */
    private Integer repayMethod;

    /**
     * 还款期数(月)
     */
    private Integer repayTerm;

    /**
     * 贷款期限(最后的结清日期)
     */
    private LocalDateTime loanDate;

    /**
     * 逾期罚息基数(130%-150%)
     */
    private BigDecimal lateChargeBase;

    /**
     * 贷款用途
     */
    private String loanPurpose;

    /**
     * 已收回利息
     */
    private BigDecimal recoveredInterest;

    /**
     * 剩余本金
     */
    private BigDecimal balance;

    /**
     * 客户联系方式
     */
    private String customerPhone;
}
