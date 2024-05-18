package com.banana.info.controller;


import com.banana.common.Result;
import com.banana.info.entity.param.DelayRecordsSearchParam;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.service.IDelayRecordsService;
import com.banana.info.service.IOverdueRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 延期记录表 前端控制器
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
@RestController
@RequestMapping("/info/delayRecords")
public class DelayRecordsController {

    @Autowired
    private IDelayRecordsService delayRecordsService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getDelayRecordsList(@RequestBody DelayRecordsSearchParam param) {
        Map<String, Object> data = delayRecordsService.getDelayRecordsList(param);
        return Result.success(data);
    }
}

