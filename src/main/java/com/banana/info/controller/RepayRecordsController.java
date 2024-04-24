package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.service.ILoanRecoverService;
import com.banana.info.service.IRepayRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 还款记录表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-24
 */
@RestController
@RequestMapping("/info/repayRecords")
public class RepayRecordsController {

    @Autowired
    private IRepayRecordsService repayRecordsService;
    @PostMapping("/list")
    public Result<Map<String, Object>> getRepayRecordsList(@RequestBody RepayRecordsSearchParam param) {
        Map<String, Object> data = repayRecordsService.getRepayRecordsList(param);
        return Result.success(data);
    }
}
