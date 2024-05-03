package com.banana.info.controller;


import com.banana.common.Result;
import com.banana.info.entity.param.*;
import com.banana.info.entity.vo.GetLoanLimitVO;
import com.banana.info.service.ICustomerLoanLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 客户贷款额度表 前端控制器
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-01
 */
@RestController
@RequestMapping("/info/customerLoanLimit")
public class CustomerLoanLimitController {

    @Autowired
    private ICustomerLoanLimitService customerLoanLimitService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getCustomerLoanLimitList(@RequestBody CustomerLoanLimitSearchParam param) {
        Map<String, Object> data = customerLoanLimitService.getCustomerLoanLimitList(param);
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<Void> addCustomerLoanLimit(@RequestBody AddCustomerLoanLimitParam param) {
        customerLoanLimitService.addCustomerLoanLimit(param);
        return Result.success("创建成功");
    }

    @PostMapping("/evaluate")
    public Result<Void> evaluateCustomerLoanLimit(@RequestHeader("X-Token") String token, @RequestBody EvaluateCustomerLoanLimitParam param) {
        customerLoanLimitService.evaluateCustomerLoanLimit(token, param);
        return Result.success("评估完成");
    }

    @PostMapping("/getLoanLimit")
    public Result<GetLoanLimitVO> getLoanLimit(@RequestBody getLoanLimitParam param) {
        return Result.success(customerLoanLimitService.getLoanLimit(param.getCustomerId()));
    }

}

