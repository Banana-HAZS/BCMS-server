package com.banana.login;

import com.banana.common.Result;
import com.banana.info.entity.LoginParam;
import com.banana.info.entity.User;
import com.banana.info.service.IUserService;
import com.banana.info.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //RestController注解会将返回结果自动转JSon
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public Result<Map<String,Object>> login(@RequestBody LoginParam param){
        Map<String,Object> data = userService.login(param);
        if(data!=null){
            return Result.success("登录成功！",data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getInfo(@RequestParam("token") String token){
        // 根据token从redis获取用户信息
        Map<String,Object> data = userService.getUserInfo(token);
        if(data!=null){
            return Result.success(data);
        }
        return Result.fail(20003,"登录已过期，请重新登录");
    }
}
