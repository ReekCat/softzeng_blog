package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-03-17 10:06:30
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    //查询用户列表
    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    //增加用户-②新增用户
    boolean checkUserNameUnique(String userName);
    boolean checkPhoneUnique(User user);
    boolean checkEmailUnique(User user);
    ResponseResult addUser(User user);

    //修改用户-②更新用户信息
    void updateUser(User user);

    ResponseResult updateGetUserInfo(Long id);
}

