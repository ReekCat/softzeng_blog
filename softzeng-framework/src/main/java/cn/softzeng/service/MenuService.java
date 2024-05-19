package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-03-20 17:06:54
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    //查询菜单列表
    List<Menu> selectMenuList(Menu menu);

    boolean whetherSon(Long id);

    ResponseResult getMenuTree();

    List<Long> selectMenuListByRoleId(Long id);
}

