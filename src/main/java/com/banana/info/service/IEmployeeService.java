package com.banana.info.service;

import com.banana.common.Result;
import com.banana.info.entity.Employee;
import com.banana.info.entity.param.LoginParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

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

    Result<Map<String,Object>> login(LoginParam param);

    Employee getUserInfo(String token);

    void logout(String token);

    @Transactional(rollbackFor = Exception.class)
    void addEmployee(Employee employee);
}
