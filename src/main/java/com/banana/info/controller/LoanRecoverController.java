package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.LoanRecoverDelayPayoffParam;
import com.banana.info.entity.param.LoanRecoverEarlyPayoffParam;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.vo.InitDelayFormVO;
import com.banana.info.service.ILoanRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询贷款收回记录-同步更新
     * @param param
     * @return
     */
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
    public Result<?> earlyPayoff(@RequestBody LoanRecoverEarlyPayoffParam param) {
        loanRecoverService.earlyPayoff(param);
        return Result.success("已提前结清");
    }

    @PostMapping("/delayPayoff")
    public Result<?> delayPayoff(@RequestBody LoanRecoverDelayPayoffParam param) {
        loanRecoverService.delayPayoff(param);
        return Result.success("当期还款期限已延期");
    }

    @GetMapping("/initDelayForm")
    public Result<InitDelayFormVO> initDelayForm() {
        return Result.success(loanRecoverService.initDelayForm());
    }
}
