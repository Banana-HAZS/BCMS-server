package com.banana.info.mapper;

import com.banana.info.entity.param.RoleParam;
import com.banana.info.entity.vo.RoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
public interface RoleVOMapper extends BaseMapper<RoleVO> {

    Page<RoleVO> getRolePage(@Param("param") RoleParam param, Page<RoleVO> page);
}
