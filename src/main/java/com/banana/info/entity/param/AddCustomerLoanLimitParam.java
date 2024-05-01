package com.banana.info.entity.param;

import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.commonEnum.EvaluateStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddCustomerLoanLimitParam implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public CustomerLoanLimit toCustomerLoanLimit(){
        CustomerLoanLimit customerLoanLimit = new CustomerLoanLimit();
        customerLoanLimit.setCustomerId(customerId);
        customerLoanLimit.setOccupation(occupation);
        customerLoanLimit.setWorkUnit(workUnit);
        customerLoanLimit.setAnnualIncome(annualIncome);
        customerLoanLimit.setCareerYears(careerYears);
        customerLoanLimit.setDebtAmount(debtAmount);
        customerLoanLimit.setHouseProperty(houseProperty);
        customerLoanLimit.setCarProperty(carProperty);
        customerLoanLimit.setMonthDebtRepay(monthDebtRepay);
        customerLoanLimit.setDti(dti);
        customerLoanLimit.setDeposit(deposit);
        customerLoanLimit.setInvest(invest);
        customerLoanLimit.setAssetValuation(assetValuation);
        customerLoanLimit.setNetAssetValue(netAssetValue);
        customerLoanLimit.setEvaluateStatus(EvaluateStatusEnum.WAIT_EVALUATE.getV());
        return customerLoanLimit;
    }
}
