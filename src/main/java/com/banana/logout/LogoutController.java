package com.banana.logout;

import com.banana.common.Result;
import com.banana.info.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestController注解会将返回结果自动转JSon
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping
    public Result<?> logout(@RequestHeader("X-Token") String token){
        employeeService.logout(token);
        return Result.success("已退出登录");
    }
}
