package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.CustomerCreditSearchParam;
import com.banana.info.entity.param.LoanApplySearchParam;
import com.banana.info.service.ICustomerCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 客户信用表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@RestController
@RequestMapping("/info/customerCredit")
public class CustomerCreditController {

    @Autowired
    private ICustomerCreditService customerCreditService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getCustomerCreditList(@RequestBody CustomerCreditSearchParam param) {
        Map<String, Object> data = customerCreditService.getCustomerCreditList(param);
        return Result.success(data);
    }
}
