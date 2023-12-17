package com.wzl.dao;

import com.wzl.po.Comment;
import com.wzl.po.Favorite;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    //获取评论，并且排序
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
