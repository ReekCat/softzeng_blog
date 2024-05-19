package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.User;

public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
