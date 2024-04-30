package com.banana.info.service;

import com.banana.info.entity.CreditScoreRecords;
import com.banana.info.entity.param.CreditScoreRecordsSearchParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 信用分变动记录表 服务类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
public interface ICreditScoreRecordsService extends IService<CreditScoreRecords> {

    Map<String, Object> getCreditScoreRecordsList(CreditScoreRecordsSearchParam param);

}
