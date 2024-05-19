package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-04-03 13:47:16
 */
public interface RoleService extends IService<SysRole> {

    ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status);

    void addRole(SysRole sysRole);

    //修改角色-保存修改好的角色信息
    void updateRole(SysRole sysRole);

    //新增用户-①查询角色列表接口
    List<SysRole> selectRoleAll();

}

