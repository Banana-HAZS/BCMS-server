package com.banana.info.service.impl;

import com.alibaba.fastjson2.JSON;
import com.banana.info.entity.Employee;
import com.banana.info.mapper.EmployeeMapper;
import com.banana.info.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
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

    @Override
    public Map<String, Object> login(Employee employee) {
        //根据用户名和密码查询员工
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getId,employee.getId());
        Employee loginEmployee = this.baseMapper.selectOne(wrapper);
        //生成token并将用户信息存入redis
        if(loginEmployee!=null && passwordEncoder.matches(employee.getPassword(),loginEmployee.getPassword())){
            String key = "Employee:" + UUID.randomUUID();
            //存入redis
            loginEmployee.setPassword(null);
            redisTemplate.opsForValue().set(key,loginEmployee,30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data=new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj!=null){
            Employee loginEmployee = JSON.parseObject(JSON.toJSONString(obj), Employee.class);
            Map<String, Object> data=new HashMap<>();
            data.put("id",loginEmployee.getId());
            data.put("name",loginEmployee.getName());
            data.put("position",loginEmployee.getPosition());
            data.put("picture",loginEmployee.getPicture());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
