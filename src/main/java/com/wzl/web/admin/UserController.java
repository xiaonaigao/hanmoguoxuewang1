package com.wzl.web.admin;

import com.wzl.po.User;
import com.wzl.service.UserService;
import com.wzl.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author wang
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    //获取所有的用户
    @GetMapping("/user")
    public String userAdmin(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", userService.listUser(pageable));
        return "admin/user";
    }

    //删除
    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/user";

    }

    //修改的接口
    @GetMapping("/user/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/user-input";
    }

    //  搜索用户
    @PostMapping("/user/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         UserQuery user, Model model) {
        model.addAttribute("page", userService.listUser(pageable, user));
        return "admin/user :: userList";
    }

    //修改方法
    //通过@Valid注解对user进行验证，如果出现注解错误就要返回编辑页面重新输入。
    //通过 @PathVariable把url里面的用户id传入到方法形参里通过userService.updateUser(id, user)方法进行信息更新。
    @PostMapping("/user/{id}")
    public String editpost(@Valid User user, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/user-input";
        }
        User u = userService.updateUser(id, user);
        if (u == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/user";
    }


}
