package com.banana.info.service;

import com.banana.info.entity.LoginParam;
import com.banana.info.entity.User;
import com.banana.info.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
public interface IUserService extends IService<User> {
    
    Map<String, Object> login(LoginParam param);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
