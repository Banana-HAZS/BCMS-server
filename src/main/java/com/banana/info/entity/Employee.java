package com.banana.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String folk;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 职位id
     */
    private Integer roleId;

    /**
     * 出生日期
     */
    // @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime birthDate;

    /**
     * 婚姻状况
     */
    private String marital;

    /**
     * 籍贯
     */
    private String hometown;

    /**
     * 现居住地
     */
    private String address;

    /**
     * 学历
     */
    private String educational;

    /**
     * 健康状况
     */
    private String health;

    /**
     * 宗教信仰
     */
    private String religious;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 头像
     */
    private String picture;

    /**
     * 注册时间
     */
    private LocalDateTime registrationDate;

}
