package cn.softzeng.service.impl;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.TagListDto;
import cn.softzeng.domain.entity.Tag;
import cn.softzeng.domain.vo.PageVo;
import cn.softzeng.domain.vo.TagListVo;
import cn.softzeng.enums.AppHttpCodeEnum;
import cn.softzeng.exception.SystemException;
import cn.softzeng.mapper.TagMapper;
import cn.softzeng.service.TagService;
import cn.softzeng.utils.BeanCopyUtils;
import cn.softzeng.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-20 10:52:52
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        if(null == tag.getName()){
            throw new SystemException(AppHttpCodeEnum.ADD_ERROR);
        }
        Long userId = SecurityUtils.getUserId();
        Date date = new Date();
        tag.setCreateBy(userId);
        tag.setCreateTime(date);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(String id) {
        if(id.contains(",")){
            List<Long> idList = Arrays.stream(id.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            tagMapper.deleteBatchIds(idList);
            return ResponseResult.okResult();
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tagById = getById(id);
        return ResponseResult.okResult(tagById);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        Long userId = SecurityUtils.getUserId();
        tag.setUpdateBy(userId);
        Date date = new Date();
        tag.setUpdateTime(date);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = list();
        List<TagListVo> tagListVos = BeanCopyUtils.copyBeanList(list, TagListVo.class);
        return ResponseResult.okResult(tagListVos);
    }



}

