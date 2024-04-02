package com.banana.info.entity.param;

import lombok.Data;
import java.io.Serializable;

@Data
public class RoleParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;
}
