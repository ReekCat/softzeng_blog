package cn.softzeng.controller;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.TagListDto;
import cn.softzeng.domain.entity.Tag;
import cn.softzeng.domain.vo.PageVo;
import cn.softzeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") String id){
        return tagService.deleteTag(id);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }

    @GetMapping("/{id}")
    private ResponseResult getTag(@PathVariable("id") Long id){
        return tagService.getTag(id);
    }

    @PutMapping
    private ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
