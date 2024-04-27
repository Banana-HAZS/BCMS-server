package com.banana.info.service;

import com.banana.info.entity.OverdueRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 逾期记录表 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-27
 */
public interface IOverdueRecordsService extends IService<OverdueRecords> {

    void updateOverdueRecords();
}
