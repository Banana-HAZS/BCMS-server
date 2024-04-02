package com.banana.info.entity.vo;

import com.banana.info.entity.Employee;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职位id
     */
    private Integer roleId;

    /**
     * 当前权限
     */
    private String roleName;

    /**
     * 在职时间(天)
     */
    private Integer workTime;

    /**
     * 累计处理业务次数
     */
    private Integer num;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 入职时间
     */
    private LocalDateTime registrationDate;

    public Employee toEmployee(){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setRoleId(roleId);
        return employee;
    }
}
