package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.GetFinancialSummaryVO;
import com.banana.info.service.IFinancialSummaryService;
import com.banana.info.service.IRepayRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 财务报表汇总 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-05-21
 */
@RestController
@RequestMapping("/info/financialSummary")
public class FinancialSummaryController {

    @Autowired
    private IFinancialSummaryService financialSummaryService;
    @GetMapping("/getFinancialSummary")
    public Result<GetFinancialSummaryVO> getFinancialSummary() {
        GetFinancialSummaryVO data = financialSummaryService.getFinancialSummary();
        return Result.success(data);
    }
}
