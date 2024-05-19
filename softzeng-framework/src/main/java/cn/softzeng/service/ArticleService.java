package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.AddArticleDto;
import cn.softzeng.domain.dto.UpdateArticleDto;
import cn.softzeng.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);
    
    ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticle(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticleById(String id);
}
