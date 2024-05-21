package com.banana.info.service;

import com.banana.info.entity.vo.GetFinancialSummaryVO;

/**
 * <p>
 * 财务报表汇总 服务类
 * </p>
 *
 * @author zjy
 * @since 2024-04-24
 */
public interface IFinancialSummaryService {

    GetFinancialSummaryVO getFinancialSummary();
}
