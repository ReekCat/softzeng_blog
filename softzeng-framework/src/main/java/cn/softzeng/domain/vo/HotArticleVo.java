package cn.softzeng.domain.vo;

import com.google.common.eventbus.AllowConcurrentEvents;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {

    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
