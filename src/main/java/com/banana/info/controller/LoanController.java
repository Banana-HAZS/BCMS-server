package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.Customer;
import com.banana.info.entity.Loan;
import com.banana.info.entity.param.AuditLoanParam;
import com.banana.info.entity.param.LoanApplyParam;
import com.banana.info.entity.param.LoanSaveParam;
import com.banana.info.entity.vo.LoanApplyVO;
import com.banana.info.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 贷款表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-02
 */
@RestController
@RequestMapping("/info/loan")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getLoanList(@RequestBody LoanApplyParam param) {
        Map<String, Object> data = loanService.getLoanList(param);
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addLoan(@RequestHeader("X-Token") String token, @RequestBody LoanSaveParam param) {
        loanService.addLoan(token, param);
        return Result.success("贷款申请成功");
    }

    @PostMapping("/audit")
    public Result<?> auditLoan(@RequestHeader("X-Token") String token, @RequestBody AuditLoanParam param) {
        loanService.auditLoan(token, param);
        return Result.success("审核成功");
    }

    @PostMapping("/reject")
    public Result<?> rejectLoan(@RequestHeader("X-Token") String token, @RequestBody AuditLoanParam param) {
        loanService.rejectLoan(token, param);
        return Result.success("审核成功");
    }

    @GetMapping("/idCard/{idCard}")
    public Result<Customer> fetchUserInfo(@PathVariable("idCard") String idCard) {
        Customer customer = loanService.getCustomerByIdCard(idCard);
        return Result.success(customer);
    }
}
