package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Article;
import cn.softzeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

 /*   @GetMapping("/list")
    public List<Article> test1(){
        return articleService.list();
    }*/
    //热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章封装成ResponseResult返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    //分类
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //阅读全文
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
