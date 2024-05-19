package cn.softzeng.controller;

import cn.softzeng.constants.SystemConstants;
import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.dto.AddCommentDto;
import cn.softzeng.domain.entity.Comment;
import cn.softzeng.service.CommentService;
import cn.softzeng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/comment/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping("/comment")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/comment/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value = "页号"),
            @ApiImplicitParam(name="pageSize",value = "每页大小")
    }
    )
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum,pageSize);
    }
}
