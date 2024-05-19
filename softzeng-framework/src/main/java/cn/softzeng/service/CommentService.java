package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2024-03-16 22:57:48
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

