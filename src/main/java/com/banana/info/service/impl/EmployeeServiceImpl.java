package com.banana.info.service.impl;

import com.alibaba.fastjson2.JSON;
import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.common.Result;
import com.banana.info.entity.Employee;
import com.banana.info.entity.param.LoginParam;
import com.banana.info.entity.vo.GetInfoVO;
import com.banana.info.mapper.EmployeeMapper;
import com.banana.info.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2023-12-19
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleServiceImpl roleService;

    @Override
    public Result<Map<String, Object>> login(LoginParam param) {
        //根据用户名和密码查询员工
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getAccount, param.getAccount());
        Employee loginEmployee = this.baseMapper.selectOne(wrapper);

        if (Objects.isNull(loginEmployee)) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        //生成token并将用户信息存入redis
        if (passwordEncoder.matches(param.getPassword(), loginEmployee.getPassword())) {
            String key = "Employee:" + UUID.randomUUID();
            //存入redis
            loginEmployee.setPassword(null);
            redisTemplate.opsForValue().set(key, loginEmployee, 30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return Result.success("登录成功！", data);
        }
        throw new BusinessException(BusinessExceptionEnum.INCORRECT_USERNAME_OR_PASSWORD);
    }

    @Override
    public GetInfoVO getUserInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        if(Objects.isNull(obj)){
            throw new BusinessException(BusinessExceptionEnum.LOGIN_EXPIRED);
        }
        Employee employee = JSON.parseObject(JSON.toJSONString(obj), Employee.class);
        GetInfoVO getInfoVO = employee.toGetInfoVO();
        getInfoVO.setMenuList(roleService.getMenuList(employee.getRoleId()));

        return getInfoVO;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public void addEmployee(Employee employee) {
        employee.setRegistrationDate(LocalDateTime.now());
        employee.setPicture("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        try {
            baseMapper.insert(employee);
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNTDUPLICATION);
        }
    }
}
