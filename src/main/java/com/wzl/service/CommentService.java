package com.wzl.service;

import com.wzl.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
public interface CommentService {
    //获取列表留言
    List<Comment> listCommentByBlogId(Long blogId);
    //发布留言
    Comment saveComment(Comment comment);
    //分页查询留言
    Page<Comment> listComment(Pageable pageable);
    List<Comment> listComment();
    //删除
    void deleteComment(Long id);


}
