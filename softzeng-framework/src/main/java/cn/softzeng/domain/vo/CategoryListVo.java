package cn.softzeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListVo {

    private Long id;

    private String description;

    private String name;

}
