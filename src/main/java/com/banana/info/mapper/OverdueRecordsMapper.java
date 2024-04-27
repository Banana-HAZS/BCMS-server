package com.banana.info.mapper;

import com.banana.info.entity.OverdueRecords;
import com.banana.info.entity.RepayRecords;
import com.banana.info.entity.param.RepayRecordsSearchParam;
import com.banana.info.entity.vo.RepayRecordsSearchVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 逾期记录表 Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2024-04-27
 */
public interface OverdueRecordsMapper extends BaseMapper<OverdueRecords> {

}
