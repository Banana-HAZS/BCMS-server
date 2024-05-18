package com.banana.info.service;

import com.banana.info.entity.CustomerCredit;
import com.banana.info.entity.param.CustomerCreditSearchParam;
import com.banana.info.entity.param.GetCreditLevelParam;
import com.banana.info.entity.param.LoanApplySearchParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 客户信用表 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
public interface ICustomerCreditService extends IService<CustomerCredit> {

    Map<String, Object> getCustomerCreditList(CustomerCreditSearchParam param);

    CustomerCredit getCreditLevel(GetCreditLevelParam param);
}
