package cn.softzeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;
    private String status;
}
