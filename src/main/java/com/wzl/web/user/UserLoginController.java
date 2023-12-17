package com.wzl.web.user;

import com.wzl.po.Blog;
import com.wzl.po.User;
import com.wzl.service.UserService;
import com.wzl.service.UserServiceImpl;
import com.wzl.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @version 1.0
 */
//登录控制器
@Controller
@RequestMapping("/user")
public class UserLoginController {
    @Autowired
    private UserService userService;

    //        跳转到登录页面
    @GetMapping
    public String loginPage() {
//        登录的路径
        return "user/login";
    }

    //    登录方法
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user=userService.checkUser(username,password);
        //登录成功
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/index";
        }else{
            //登录失败
            attributes.addFlashAttribute("message","用户名和密码错误");

            return "redirect:/user/login";
        }
    }
    //下拉框退出方法
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";

    }
    //登录跳转页面
    @GetMapping("/login")
    public String Userlogin(){
        return "user/login";
    }
    //注册跳转 页面
    @GetMapping("/userRegister")
    public String userRegister(){
        return "user/userRegister";

    }
    //注册方法
    //在注册时进行用户名查找，user1不为空说明用户名已存在返回到登录页面，user1为空说明可以注册，并且直接登录到主页。
    @PostMapping("/userRegister")
    public String post(User user,HttpSession session, RedirectAttributes attributes){
        User user1=userService.checkUsername(user.getUsername());
        if(user1!=null){
            attributes.addFlashAttribute("message","用户存在，直接登录");
            return "redirect:/user/login";
        }else{
            user.setPassword(MD5Utils.code(user.getPassword()));
            userService.saveUser(user);
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/index";
        }

    }
}
