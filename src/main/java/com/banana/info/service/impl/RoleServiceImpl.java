package com.banana.info.service.impl;

import com.banana.info.entity.param.RoleParam;
import com.banana.info.entity.vo.RoleVO;
import com.banana.info.mapper.RoleMapper;
import com.banana.info.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleVO> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Map<String, Object> getRolePage(RoleParam param) {

        Page<RoleVO> page = new Page<>(param.getPageNo(), param.getPageSize());

        roleMapper.getRolePage(param,page);

        List<RoleVO> records = page.getRecords();
        records.forEach(roleVO -> {
            if(Objects.isNull(roleVO.getNum())){
                roleVO.setNum(0);
            }
        });

        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",records);
        return data;
    }
}
