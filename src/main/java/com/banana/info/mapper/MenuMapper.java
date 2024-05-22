package com.banana.info.mapper;

import com.banana.info.entity.Menu;
import com.banana.info.entity.vo.MenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-22
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuVO> getFirstMenu(@Param("authority") List<Integer> authority);

    List<MenuVO> getSecondMenu(@Param("id") Integer id, @Param("authority") List<Integer> authority);
}
