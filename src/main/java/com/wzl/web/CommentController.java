package com.wzl.web;

import com.wzl.po.Comment;
import com.wzl.po.Favorite;
import com.wzl.po.User;
import com.wzl.service.BlogService;
import com.wzl.service.CommentService;
import com.wzl.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @version 1.0
 */
@Controller

public class CommentController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;
    //根据博客的id，来获取评论的列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    //点击发布按钮，提交评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        //获取博客id，并设置给评论
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
    //收藏表
    //通过session获取用户对象，把目前用户id存入到收藏表，favorite对象里面有文章id，然后一起存入到收藏表
    @PostMapping("/favorite")
    public String post1(Favorite favorite,HttpSession session) {
        User user = (User) session.getAttribute("user");
        favorite.setUserid(user.getId());
        favoriteService.saveFavorite(favorite);
        return null;
    }
    //查询收藏
    @GetMapping("/favorite")
    public String userAdmin(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", favoriteService.listFavorite(pageable));
        return "favorite";
    }
    //取消收藏
    @GetMapping("/favorite/{id}/delete")
    public String delete(@PathVariable Long id) {
        favoriteService.deleteBlog(id);
        return "redirect:/favorite";

    }


}
