package cn.softzeng.mapper;

import cn.softzeng.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-20 17:06:52
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
    //修改角色-根据角色id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}
