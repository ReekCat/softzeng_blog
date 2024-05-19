package cn.softzeng.service;

import cn.softzeng.domain.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2024-04-05 14:07:46
 */
public interface RoleMenuService extends IService<RoleMenu> {
    //修改角色-保存修改好的角色信息
    void deleteRoleMenuByRoleId(Long id);
}

