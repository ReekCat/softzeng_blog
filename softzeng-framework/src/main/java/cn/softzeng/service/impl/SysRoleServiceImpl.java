package cn.softzeng.service.impl;

import cn.softzeng.domain.entity.SysRole;
import cn.softzeng.mapper.SysRoleMapper;
import cn.softzeng.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2024-03-20 17:18:24
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息

        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    //-----------------------修改用户-①根据id查询用户信息----------------------------
    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }

}

