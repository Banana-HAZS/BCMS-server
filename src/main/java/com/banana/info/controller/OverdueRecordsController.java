package com.banana.info.controller;

import com.banana.common.Result;
import com.banana.info.entity.param.OverdueRecordsSearchParam;
import com.banana.info.service.IOverdueRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 逾期记录表 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2024-04-27
 */
@RestController
@RequestMapping("/info/overdueRecords")
public class OverdueRecordsController {

    @Autowired
    private IOverdueRecordsService overdueRecordsService;

    @PostMapping("/list")
    public Result<Map<String, Object>> getOverdueRecordsList(@RequestBody OverdueRecordsSearchParam param) {
        Map<String, Object> data = overdueRecordsService.getOverdueRecordsList(param);
        return Result.success(data);
    }
}
