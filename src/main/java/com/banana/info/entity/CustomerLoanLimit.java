package com.banana.info.entity;

import java.math.BigDecimal;

import com.banana.info.entity.commonEnum.LoanLimitEnum;
import com.banana.info.entity.vo.GetLoanLimitVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户贷款额度表
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerLoanLimit implements Serializable {

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
     * 从事行业年限
     */
    private Integer careerYears;

    /**
     * 负债金额(元)
     */
    private BigDecimal debtAmount;

    /**
     * 房产估值
     */
    private BigDecimal houseProperty;

    /**
     * 车产估值
     */
    private BigDecimal carProperty;

    /**
     * 每月用于还债金额(元)
     */
    private BigDecimal monthDebtRepay;

    /**
     * 债务收入比
     */
    private BigDecimal dti;

    /**
     * 储蓄(元)
     */
    private BigDecimal deposit;

    /**
     * 投资金额(元)
     */
    private BigDecimal invest;

    /**
     * 资产估值(元)
     */
    private BigDecimal assetValuation;

    /**
     * 资产净值(元)
     */
    private BigDecimal netAssetValue;

    /**
     * 贷款额度等级S(100亿元)、A+(10亿元)、A(1亿元)、A-(1千万元)、B(1百万元)、C+(50万元)、C(10万元)、D(1万元)、E(5千元)、F(2千元)、G(0元)
     */
    private String loanLimitLevel;

    /**
     * 评估状态(1待评估 2已评估)
     */
    private Integer evaluateStatus;

    /**
     * 评估日期
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

    public GetLoanLimitVO toGetLoanLimitVO(){
        GetLoanLimitVO getLoanLimitVO = new GetLoanLimitVO();
        getLoanLimitVO.setId(id);
        getLoanLimitVO.setLoanLimitLevel(loanLimitLevel);
        LoanLimitEnum loanLimitEnum = LoanLimitEnum.getEnumByCode(loanLimitLevel);
        getLoanLimitVO.setLoanLimitLevelName(loanLimitEnum.getName());
        getLoanLimitVO.setLoanLimit(loanLimitEnum.getLimit());
        getLoanLimitVO.setEvaluateStatus(evaluateStatus);
        return getLoanLimitVO;
    }
}
