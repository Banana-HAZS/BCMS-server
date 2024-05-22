package com.banana.login;

import com.banana.common.Result;
import com.banana.info.entity.param.LoginParam;
import com.banana.info.entity.vo.GetInfoVO;
import com.banana.info.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Result<GetInfoVO> getInfo(@RequestParam("token") String token){
        return Result.success(employeeService.getUserInfo(token));
    }
}
