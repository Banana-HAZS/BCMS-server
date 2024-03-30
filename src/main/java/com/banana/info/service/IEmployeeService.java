package com.banana.info.service;

import com.banana.info.entity.Employee;
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
public interface IEmployeeService extends IService<Employee> {

    Map<String, Object> login(Employee employee);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
