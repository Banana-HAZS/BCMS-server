package com.banana.info.entity;

import com.banana.info.entity.vo.MenuVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String component;

    private String path;

    private String redirect;

    private String name;

    private String title;

    private String icon;

    private Integer parentId;

    private String isLeaf;

    private Boolean hidden;

    public MenuVO toMenuVO(){
        MenuVO menuVO = new MenuVO();
        menuVO.setId(id);
        menuVO.setComponent(component);
        menuVO.setPath(path);
        menuVO.setRedirect(redirect);
        menuVO.setName(name);
        menuVO.setTitle(title);
        menuVO.setIcon(icon);
        menuVO.setParentId(parentId);
        menuVO.setIsLeaf(isLeaf);
        menuVO.setHidden(hidden);
        menuVO.setChildren(null);
        return menuVO;
    }
}
