package com.banana.info.service;

import com.banana.info.entity.Loan;
import com.banana.info.entity.LoanRecover;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.param.LoanRecoverRepayParam;
import com.banana.info.entity.param.LoanRecoverSearchParam;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-24
 */
public interface IRepayRecordsService extends IService<RepayRecords> {

    Map<String, Object> getRepayRecordsList(RepayRecordsSearchParam param);
}
