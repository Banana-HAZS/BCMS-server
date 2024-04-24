package com.banana.info.mapper;

import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 还款记录 Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2024-04-23
 */
public interface RepayRecordsMapper extends BaseMapper<RepayRecords> {

    Page<RepayRecordsSearchVO> getRepayRecordsList(@Param("param") RepayRecordsSearchParam param, Page<Object> objectPage);
}
