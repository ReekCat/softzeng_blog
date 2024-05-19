package cn.softzeng.mapper;

import cn.softzeng.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ArticleMapper extends  BaseMapper<Article>{
    void deleteByArticleId(Long id);
}