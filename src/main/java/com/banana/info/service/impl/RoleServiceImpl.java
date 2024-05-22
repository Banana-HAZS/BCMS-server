package com.banana.info.service.impl;

import com.banana.info.entity.Role;
import com.banana.info.entity.vo.MenuVO;
import com.banana.info.mapper.MenuMapper;
import com.banana.info.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限配置表 服务实现类
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMapper roleMapper;

    public List<MenuVO> getMenuList(Integer roleId){
        Role role = roleMapper.selectById(roleId);
        List<Integer> authority = Arrays.stream(role.getAuthority().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // 1. 获取一级菜单
        List<MenuVO> menuList = menuMapper.getFirstMenu(authority);
        // 2. 获取二级菜单
        menuList.forEach(menu -> {
            List<MenuVO> secondMenu = menuMapper.getSecondMenu(menu.getId(), authority);
            secondMenu.forEach(sMenu -> {
                sMenu.setChildren(Collections.emptyList());
            });
            menu.setChildren(secondMenu);
        });
        return menuList;
    }
}
