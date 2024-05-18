package com.banana.info.service.impl;

import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.commonEnum.*;
import com.banana.info.entity.param.CustomerCreditSearchParam;
import com.banana.info.entity.param.GetCreditLevelParam;
import com.banana.info.entity.vo.CustomerCreditSearchVO;
import com.banana.info.entity.vo.GetLoanLimitVO;
import com.banana.info.mapper.CustomerCreditMapper;
import com.banana.info.mapper.CustomerLoanLimitMapper;
import com.banana.info.service.ICustomerCreditService;
import com.banana.info.service.ICustomerLoanLimitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 客户信用表 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@Service
public class CustomerCreditServiceImpl extends ServiceImpl<CustomerCreditMapper, CustomerCredit> implements ICustomerCreditService {

    @Resource
    private CustomerCreditMapper customerCreditMapper;

    @Resource
    private ICustomerLoanLimitService customerLoanLimitService;

    @Resource
    private CustomerLoanLimitMapper customerLoanLimitMapper;

    @Override
    public Map<String, Object> getCustomerCreditList(CustomerCreditSearchParam param) {

        Page<CustomerCreditSearchVO> page = customerCreditMapper
                .getCustomerCreditList(param, new Page<>(param.getPageNo(), param.getPageSize()));

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return data;
    }

    @Override
    public CustomerCredit getCreditLevel(GetCreditLevelParam param) {
        return customerCreditMapper.selectOne(new LambdaQueryWrapper<CustomerCredit>()
                .eq(CustomerCredit::getCustomerId,param.getCustomerId())
        );
    }

    public void updateCustomerCreditScore(Integer customerId, CreditScoreChangeTypeEnum typeEnum) {
        CustomerCredit customerCredit = customerCreditMapper.selectOne(
                new LambdaQueryWrapper<CustomerCredit>()
                .eq(CustomerCredit::getCustomerId, customerId));

        // 更新信用分、信用分等级
        String oldCreditLevel = customerCredit.getCreditLevel();
        customerCredit.setCreditScore(customerCredit.getCreditScore() + typeEnum.getValue());
        CreditTypeEnum creditType = CreditTypeEnum.getCreditByScore(customerCredit.getCreditScore());
        customerCredit.setCreditLevel(creditType.getCode());

        customerCreditMapper.update(null, new LambdaUpdateWrapper<CustomerCredit>()
                .eq(CustomerCredit::getCustomerId, customerId)
                .set(CustomerCredit::getCreditScore, customerCredit.getCreditScore())
                .set(CustomerCredit::getCreditLevel, customerCredit.getCreditLevel())
        );

        // 信用分影响贷款额度(信用等级增加一级，则贷款额度上升一级；信用等级减少一级，则贷款额度下降一级)
        // 没有变更
        if (customerCredit.getCreditLevel().equals(oldCreditLevel)) {
            return;
        }

        // 当前贷款额度
        GetLoanLimitVO loanLimit = customerLoanLimitService.getLoanLimitByCustomer(customerId);
        LoanLimitEnum loanLimitEnum = LoanLimitEnum.getEnumByCode(loanLimit.getLoanLimitLevel());
        LoanLimitEnum[] values = LoanLimitEnum.values();

        // 新的贷款额度
        String newLoanLimit = "";

        // 向上变更
        if (typeEnum.getValue() > 0) {
            // 已经是最大贷款额度，不再变更
            if (loanLimitEnum.ordinal() == 0) {
                return;
            }

            newLoanLimit = values[loanLimitEnum.ordinal() - 1].getCode();
        }

        // 向下变更
        if (typeEnum.getValue() < 0) {
            // 已经是最小贷款额度，不再变更
            if (loanLimitEnum.ordinal() == values.length-1) {
                return;
            }

            newLoanLimit = values[loanLimitEnum.ordinal() + 1].getCode();
        }

        // 创建新的贷款额度评估记录
        CustomerLoanLimit customerLoanLimit = new CustomerLoanLimit();
        customerLoanLimit.setCustomerId(customerId);
        customerLoanLimit.setEvaluatorId(SysConfig.SYSTEM.getV().intValue());
        customerLoanLimit.setEvaluateStatus(EvaluateStatusEnum.EVALUATED.getV());
        customerLoanLimit.setCreateDate(LocalDateTime.now());
        customerLoanLimit.setEvaluateDate(LocalDateTime.now());
        customerLoanLimit.setLoanLimitLevel(newLoanLimit);

        customerLoanLimitMapper.insert(customerLoanLimit);
    }
}
