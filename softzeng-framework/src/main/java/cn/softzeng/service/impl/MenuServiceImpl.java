package cn.softzeng.service.impl;

import cn.softzeng.constants.SystemConstants;
import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Menu;
import cn.softzeng.domain.vo.MenuTreeVo;
import cn.softzeng.mapper.MenuMapper;
import cn.softzeng.service.MenuService;
import cn.softzeng.utils.SecurityUtils;
import cn.softzeng.utils.SystemConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-03-20 17:06:54
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            //返回所有的权限
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return  perms;
        }
        //否则返回去所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        //判断是否是管理员
        List<Menu> menus = null;
        if(SecurityUtils.isAdmin()){
            //如果是 返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();


        }else {
            //否则 获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单 然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);;
        return menus;
    }

    @Override
    public boolean whetherSon(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Menu::getParentId,id);
        return count(wrapper) != 0;
    }

    @Override
    public ResponseResult getMenuTree() {
        List<Menu> menus = selectMenuList(new Menu());
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(menuTreeVos);
    }

    //--------------------------修改角色-根据角色id查询对应角色菜单列表树---------------------

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /***
     * 获取传入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());

        return childrenList;
    }
}

