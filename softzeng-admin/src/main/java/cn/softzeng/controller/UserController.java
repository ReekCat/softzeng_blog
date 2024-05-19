package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.User;
import cn.softzeng.enums.AppHttpCodeEnum;
import cn.softzeng.exception.SystemException;
import cn.softzeng.service.UserService;
import cn.softzeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    //--------------------------------查询用户列表-------------------------------------

    @GetMapping("/list")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }

    @DeleteMapping("/{userIds}")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }

    //-------------------------修改用户-②更新用户信息--------------------------------

    @PutMapping
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }

    @GetMapping("{id}")
    public ResponseResult updateGetUserInfo(@PathVariable Long id){
        return userService.updateGetUserInfo(id);
    }
}