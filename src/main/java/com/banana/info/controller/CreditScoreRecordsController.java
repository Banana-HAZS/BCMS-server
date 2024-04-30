package com.banana.info.controller;


import com.banana.common.Result;
import com.banana.info.entity.param.CreditScoreRecordsSearchParam;
import com.banana.info.entity.param.LoanApplySearchParam;
import com.banana.info.service.ICreditScoreRecordsService;
import com.banana.info.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 信用分变动记录表 前端控制器
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@RestController
@RequestMapping("/info/creditScoreRecords")
public class CreditScoreRecordsController {

    @Autowired
    private ICreditScoreRecordsService creditScoreRecordsService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getCreditScoreRecordsList(@RequestBody CreditScoreRecordsSearchParam param) {
        Map<String, Object> data = creditScoreRecordsService.getCreditScoreRecordsList(param);
        return Result.success(data);
    }
}

