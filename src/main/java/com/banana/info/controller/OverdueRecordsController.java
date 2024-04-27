package com.banana.info.controller;

import com.banana.info.service.IOverdueRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
