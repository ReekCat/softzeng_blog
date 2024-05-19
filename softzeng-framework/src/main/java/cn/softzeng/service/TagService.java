package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.TagListDto;
import cn.softzeng.domain.entity.Tag;
import cn.softzeng.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-03-20 10:52:52
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(String id);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();

}

