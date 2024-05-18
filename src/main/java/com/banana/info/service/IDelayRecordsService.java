package com.banana.info.service;

import com.banana.info.entity.DelayRecords;
import com.banana.info.entity.param.DelayRecordsSearchParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 延期记录表 服务类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
public interface IDelayRecordsService extends IService<DelayRecords> {

    Map<String, Object> getDelayRecordsList(DelayRecordsSearchParam param);
}
