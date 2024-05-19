package cn.softzeng.service.impl;

import cn.softzeng.constants.SystemConstants;
import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.AddArticleDto;
import cn.softzeng.domain.dto.UpdateArticleDto;
import cn.softzeng.domain.entity.Article;
import cn.softzeng.domain.entity.ArticleTag;
import cn.softzeng.domain.entity.Category;
import cn.softzeng.domain.vo.*;
import cn.softzeng.mapper.ArticleMapper;
import cn.softzeng.mapper.ArticleTagMapper;
import cn.softzeng.service.ArticleService;
import cn.softzeng.service.ArticleTagService;
import cn.softzeng.service.CategoryService;
import cn.softzeng.utils.BeanCopyUtils;
import cn.softzeng.utils.RedisCache;
import cn.softzeng.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        for (Article article : articles) {
            //从redis中获取viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page  = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        for (Article article : articles) {
            //从redis中获取viewCount
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        //查询categoryName
        articles.stream()
              .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
              .collect(Collectors.toList());
        //articleId去查询articleName进行设置

       /* for (Article article : articles) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());

        }
*/

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis对应id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        redisCache.incrementCacheMapValue("article:viewCount",article.getId().toString(),0);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        //分页查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(title),Article::getTitle,title);
        queryWrapper.like(StringUtils.hasText(summary),Article::getSummary,summary);
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        List<ArticleAdminListVo> articleAdminListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleAdminListVo.class);
        //封装数据返回
        PageVo pageVo = new PageVo(articleAdminListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticle(Long id) {
        Article articleById = getById(id);
        GetArticleVo getArticleVo = BeanCopyUtils.copyBean(articleById, GetArticleVo.class);
        List<Long> tagsByArticleId = articleTagMapper.getTagsByArticleId(id);
        getArticleVo.setTags(tagsByArticleId);
        return ResponseResult.okResult(getArticleVo);
    }

    @Override
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        Long userId = SecurityUtils.getUserId();
        updateArticleDto.setUpdateBy(userId);
        //更新 博客
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        //先删除原来的关联
        articleTagMapper.deleteTagsByArticleId(updateArticleDto.getId());
        //然后再添加新的关联
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //更新 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticleById(String id) {
        if(id.contains(",")){
            List<Long> idList = Arrays.stream(id.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            articleMapper.deleteBatchIds(idList);
            return ResponseResult.okResult();
        }
        removeById(Long.parseLong(id));
        System.out.println("王五编写的代码");
        return ResponseResult.okResult();
    }
}
