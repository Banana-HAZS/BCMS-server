package com.banana.info.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVO {

    /**
     * 唯一标识
     */
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

    /**
     * 子菜单
     */
    private List<MenuVO> children;
}
