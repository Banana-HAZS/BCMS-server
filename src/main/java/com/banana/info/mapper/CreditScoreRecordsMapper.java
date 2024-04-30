package com.banana.info.mapper;

import com.banana.info.entity.CreditScoreRecords;
import com.banana.info.entity.param.CreditScoreRecordsSearchParam;
import com.banana.info.entity.vo.CreditScoreRecordsSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 信用分变动记录表 Mapper 接口
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
public interface CreditScoreRecordsMapper extends BaseMapper<CreditScoreRecords> {

    Page<CreditScoreRecordsSearchVO> getCreditScoreRecordsList(@Param("param") CreditScoreRecordsSearchParam param, Page<CreditScoreRecordsSearchVO> objectPage);
}
