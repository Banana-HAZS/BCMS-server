package com.banana.info.service;

import com.banana.info.entity.param.RoleParam;
import com.banana.info.entity.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
public interface IRoleVOService extends IService<RoleVO> {


    Map<String,Object> getRolePage(RoleParam param);
}
