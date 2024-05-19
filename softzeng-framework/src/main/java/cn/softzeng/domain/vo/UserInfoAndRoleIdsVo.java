package cn.softzeng.domain.vo;

import cn.softzeng.domain.entity.Role;
import cn.softzeng.domain.entity.SysRole;
import cn.softzeng.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {
    private User user;
    private List<SysRole> roles;
    private List<Long> roleIds;
}