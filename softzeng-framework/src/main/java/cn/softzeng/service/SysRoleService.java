package cn.softzeng.service;

import cn.softzeng.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2024-03-20 17:18:24
 */
public interface SysRoleService extends IService<SysRole> {

    List<String> selectRoleKeyByUserId(Long id);
    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}

