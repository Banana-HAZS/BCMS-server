package com.banana.info.mapper;

import com.banana.info.entity.DelayRecords;
import com.banana.info.entity.param.DelayRecordsSearchParam;
import com.banana.info.entity.vo.DelayRecordsSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 延期记录表 Mapper 接口
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-04-30
 */
public interface DelayRecordsMapper extends BaseMapper<DelayRecords> {

    Page<DelayRecordsSearchVO> getDelayRecordsList(@Param("param") DelayRecordsSearchParam param, Page<DelayRecordsSearchVO> page);
}
