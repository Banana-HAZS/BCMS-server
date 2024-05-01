package com.banana.info.service;

import com.banana.info.entity.CustomerLoanLimit;
import com.banana.info.entity.param.AddCustomerLoanLimitParam;
import com.banana.info.entity.param.CustomerLoanLimitSearchParam;
import com.banana.info.entity.param.EvaluateCustomerLoanLimitParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 客户贷款额度表 服务类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-01
 */
public interface ICustomerLoanLimitService extends IService<CustomerLoanLimit> {

    Map<String, Object> getCustomerLoanLimitList(CustomerLoanLimitSearchParam param);

    void addCustomerLoanLimit(AddCustomerLoanLimitParam param);

    void evaluateCustomerLoanLimit(String token, EvaluateCustomerLoanLimitParam param);
}
