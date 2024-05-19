package cn.softzeng.service.impl;

import cn.softzeng.domain.entity.UserRole;
import cn.softzeng.mapper.UserRoleMapper;
import cn.softzeng.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-04-05 17:11:37
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

