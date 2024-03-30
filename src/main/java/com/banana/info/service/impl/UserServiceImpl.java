package com.banana.info.service.impl;

import com.alibaba.fastjson2.JSON;
import com.banana.info.entity.LoginParam;
import com.banana.info.entity.User;
import com.banana.info.entity.User;
import com.banana.info.mapper.UserMapper;
import com.banana.info.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjy
 * @since 2024-03-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(LoginParam param) {
        //根据用户名和密码查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount,String.valueOf(param.getId()));
        User loginUser = this.baseMapper.selectOne(wrapper);
        //生成token并将用户信息存入redis
        if(Objects.nonNull(loginUser) && passwordEncoder.matches(param.getPassword(),loginUser.getPassword())){
            String key = "User:" + UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
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
        if(Objects.nonNull(obj)){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data=new HashMap<>();
            data.put("account",loginUser.getAccount());
            data.put("name",loginUser.getName());
            data.put("gender",loginUser.getGender());
            data.put("picture",loginUser.getPicture());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
