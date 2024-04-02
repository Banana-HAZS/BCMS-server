package com.banana.login;

import com.banana.common.BusinessException;
import com.banana.common.BusinessExceptionEnum;
import com.banana.common.Result;
import com.banana.info.entity.Employee;
import com.banana.info.entity.param.LoginParam;
import com.banana.info.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController //RestController注解会将返回结果自动转JSon
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping
    public Result<Map<String,Object>> login(@RequestBody LoginParam param){
       return employeeService.login(param);
    }

    @GetMapping("/info")
    public Result<Employee> getInfo(@RequestParam("token") String token){
        // 根据token从redis获取用户信息
        Employee employee = employeeService.getUserInfo(token);
        if(Objects.nonNull(employee)){
            return Result.success(employee);
        }
        throw new BusinessException(BusinessExceptionEnum.LOGIN_EXPIRED);
    }
}
