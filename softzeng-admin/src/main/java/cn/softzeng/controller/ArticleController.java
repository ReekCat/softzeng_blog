package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.AddArticleDto;
import cn.softzeng.domain.dto.UpdateArticleDto;
import cn.softzeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("/list")
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize,String title,String summary){
        return articleService.adminArticleList(pageNum,pageSize,title,summary);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateArticleDto updateArticleDto){
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") String id){
        return articleService.deleteArticleById(id);
    }


}
