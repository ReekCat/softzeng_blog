package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
