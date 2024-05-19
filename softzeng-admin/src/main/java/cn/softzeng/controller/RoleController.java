package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Role;
import cn.softzeng.domain.entity.SysRole;
import cn.softzeng.domain.dto.ChangeRoleStatusDto;
import cn.softzeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult listRole(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.listRole(pageNum,pageSize,roleName,status);
    }

    @GetMapping("{id}")
    public ResponseResult getSysRoleInfo(@PathVariable Long id){
        SysRole roleServiceById = roleService.getById(id);
        return ResponseResult.okResult(roleServiceById);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto){
        SysRole sysRole = new SysRole();
        sysRole.setId(changeRoleStatusDto.getRoleId());
        sysRole.setStatus(changeRoleStatusDto.getStatus());
        roleService.updateById(sysRole);
        return ResponseResult.okResult();
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody SysRole sysRole){
         roleService.addRole(sysRole);
         return ResponseResult.okResult();
    }

    @PutMapping
    public ResponseResult edit(@RequestBody SysRole sysRole) {
        roleService.updateRole(sysRole);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllRole")
    //①查询角色列表接口
    public ResponseResult listAllRole(){
        List<SysRole> sysRoles = roleService.selectRoleAll();
        return ResponseResult.okResult(sysRoles);
    }
}
