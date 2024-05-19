package cn.softzeng.service.impl;

import cn.softzeng.constants.SystemConstants;
import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Article;
import cn.softzeng.domain.vo.CategoryListVo;
import cn.softzeng.domain.vo.CategoryVo;
import cn.softzeng.domain.vo.PageVo;
import cn.softzeng.service.ArticleService;
import cn.softzeng.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.softzeng.domain.entity.Category;
import cn.softzeng.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import cn.softzeng.service.CategoryService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 23:26:56
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为已发布的
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);

                categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        List<Category> list = list();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(list, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVos);
    }

    //----------------------------------分页查询分类列表------------------------------------------

    @Override
    public PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Category> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}

