package cn.softzeng.mapper;

import cn.softzeng.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-20 17:18:23
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleKeyByUserId(Long userId);
    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}
