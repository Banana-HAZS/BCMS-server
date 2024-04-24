package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.LoanApplyParam;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.service.ILoanRecoverService;
import com.banana.info.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 贷款收回表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@RestController
@RequestMapping("/info/loanRecover")
public class LoanRecoverController {

    @Autowired
    private ILoanRecoverService loanRecoverService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getLoanRecoverList(@RequestBody LoanRecoverSearchParam param) {
        Map<String, Object> data = loanRecoverService.getLoanRecoverList(param);
        return Result.success(data);
    }

    @PostMapping("/repay")
    public Result<?> repay(@RequestBody LoanRecoverRepayParam param) {
        loanRecoverService.repay(param);
        return Result.success("还款成功");
    }

    @PostMapping("/earlyPayoff")
    public Result<?> earlyPayoff(@RequestBody LoanRecoverRepayParam param) {
        loanRecoverService.earlyPayoff(param);
        return Result.success("已提前结清");
    }
}
