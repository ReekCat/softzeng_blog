package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Menu;
import cn.softzeng.domain.vo.MenuTreeVo;
import cn.softzeng.domain.vo.MenuVo;
import cn.softzeng.domain.vo.RoleMenuTreeSelectVo;
import cn.softzeng.mapper.MenuMapper;
import cn.softzeng.service.MenuService;
import cn.softzeng.utils.BeanCopyUtils;
import cn.softzeng.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("/list")
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable Long id){
        Menu menuById = menuService.getById(id);
        return ResponseResult.okResult(menuById);
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id){
        if(menuService.whetherSon(id)){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return menuService.getMenuTree();
    }

    //---------------------修改角色-根据角色id查询对应角色菜单列表树--------------------------

    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }

}
