package cn.softzeng.service.impl;

import cn.softzeng.constants.SystemConstants;
import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Role;
import cn.softzeng.domain.entity.RoleMenu;
import cn.softzeng.domain.entity.SysRole;
import cn.softzeng.domain.vo.PageVo;
import cn.softzeng.domain.vo.SysRoleVo;
import cn.softzeng.mapper.RoleMapper;
import cn.softzeng.service.RoleMenuService;
import cn.softzeng.service.RoleService;
import cn.softzeng.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-04-03 13:47:17
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public ResponseResult listRole(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),SysRole::getRoleName,roleName);
        queryWrapper.like(StringUtils.hasText(status),SysRole::getStatus,status);
        queryWrapper.orderByAsc(SysRole::getRoleSort);
        Page<SysRole> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        List<SysRoleVo> sysRoleVos = BeanCopyUtils.copyBeanList(page.getRecords(), SysRoleVo.class);
        PageVo pageVo = new PageVo(sysRoleVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }


    @Override
    @Transactional
    public void addRole(SysRole sysRole) {
        save(sysRole);
        if(sysRole.getMenuIds()!=null&&sysRole.getMenuIds().length>0){
            insertRoleMenu(sysRole);
        }
    }

    @Override
    public void updateRole(SysRole sysRole) {
        updateById(sysRole);
        roleMenuService.deleteRoleMenuByRoleId(sysRole.getId());
        insertRoleMenu(sysRole);
    }



    @Override
    public List<SysRole> selectRoleAll() {
        return list(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, SystemConstants.NORMAL));
    }


    private void insertRoleMenu(SysRole sysRole) {
        List<RoleMenu> roleMenuList = Arrays.stream(sysRole.getMenuIds())
                .map(memuId -> new RoleMenu(sysRole.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }

}

