package com.banana.info.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限配置表
 * </p>
 *
 * @author ZhaiJianYu
 * @since 2024-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
      private Integer id;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 权限
     */
    private String authority;

    /**
     * 逻辑删除
     */
    private Integer deleted;


}
