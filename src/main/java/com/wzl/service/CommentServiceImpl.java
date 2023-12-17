package com.wzl.service;

import com.wzl.dao.CommentRepository;
import com.wzl.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    //获取留言
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }
    //发布留言
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        //回复的是父类，获取父类对象
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        //创建文章的时间
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    //获取留言列表
    @Transactional
    @Override
    public Page<Comment> listComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public List<Comment> listComment() {
        return commentRepository.findAll();
    }

//删除留言
    @Transactional
    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
//        System.out.println("顶级comments:"+comments);
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComents();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComents(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComents().size()>0) {
            List<Comment> replys = comment.getReplyComents();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComents().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}

