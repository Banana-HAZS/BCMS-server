package com.banana.info.service;

import com.banana.info.entity.OverdueRecords;
import com.banana.info.entity.param.OverdueRecordsSearchParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    void batchAddOverdueRecords(List<Integer> overdueRecoverIds);

    Map<String, Object> getOverdueRecordsList(OverdueRecordsSearchParam param);

    OverdueRecords getOverdueRecordsByLoanRecover(Integer id);
}
