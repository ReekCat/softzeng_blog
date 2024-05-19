package cn.softzeng.service.impl;

import cn.softzeng.domain.entity.RoleMenu;
import cn.softzeng.mapper.RoleMenuMapper;
import cn.softzeng.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-04-05 14:07:48
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    //删除角色对应的权限
    @Override
    public void deleteRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        remove(queryWrapper);
    }
}

