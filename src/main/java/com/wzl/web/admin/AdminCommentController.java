package com.wzl.web.admin;

import com.wzl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author wang
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;
    //留言列表展示
    @GetMapping("/comment")
    public String comment(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                  Pageable pageable, Model model){
        model.addAttribute("page",commentService.listComment(pageable));
        return"admin/comment";
    }
    //删除留言, 通过 @PathVariable把url里面的评论id传入到方法形参里，
    // 然后根据deleteComment(id)删除对应的留言，删除成功后会有相应的提示。
    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/comment";
    }
}
