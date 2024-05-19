package cn.softzeng.mapper;

import cn.softzeng.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-26 00:27:32
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<Long> getTagsByArticleId(Long articleId);
    void deleteTagsByArticleId(Long articleId);
}
