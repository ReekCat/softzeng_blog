package cn.softzeng.service.impl;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.LoginUser;
import cn.softzeng.domain.entity.User;
import cn.softzeng.service.LoginService;
import cn.softzeng.utils.JwtUtil;
import cn.softzeng.utils.RedisCache;
import cn.softzeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成toke
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);


        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登录用户的id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }

}
