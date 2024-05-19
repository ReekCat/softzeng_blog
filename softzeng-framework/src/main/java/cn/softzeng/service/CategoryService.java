package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.softzeng.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-14 23:26:54
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    //分页查询分类列表
    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}

