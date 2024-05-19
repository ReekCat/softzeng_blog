package cn.softzeng.domain.vo;

import cn.softzeng.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<Menu> menus;
}
