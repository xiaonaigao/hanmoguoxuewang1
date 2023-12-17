package com.wzl.web.admin;

import com.wzl.po.Comment;
import com.wzl.po.Favorite;
import com.wzl.po.User;
import com.wzl.service.FavoriteService;
import com.wzl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private UserService userService;

	//        跳转到登录页面
	@GetMapping
	public String loginPage() {
//        登录的路径
		return "admin/login";
	}

	//    登录方法
	@PostMapping("/login")
	public String login(@RequestParam String username,
	                    @RequestParam String password,
	                    HttpSession session,
	                    RedirectAttributes attributes) {
		User user = userService.checkUser(username, password);
//   如果user不为空并且类型为1 员登录管理页面
		if (user != null && user.getType() == 1) {
//     用户不为空将用户传递到登录页面，但是密码不要传递过去，不安全
			user.setPassword(null);
			session.setAttribute("user", user);
			session.setAttribute("quanxian", "1");
			return "admin/index";
			// 如果user不为空并且类型为2 用户权限不足
		} else if (user != null && user.getType() == 2) {
			attributes.addFlashAttribute("message", "权限不足，无法登录");
			// 不要直接去admin因为重新登陆就会有问题，因此使用重定向
			return "redirect:/admin";

		} else {
			//    如果user为空登录失败,用户名密码不对的时候返回到登陆页面，给前端页面一个提示
			attributes.addFlashAttribute("message", "用户名和密码错误");
			return "redirect:/admin";
		}
	}

	//用户退出的方法
	@GetMapping("/logout")
	public String logout(HttpSession session) {
//        退出后把session情况
		session.removeAttribute("user");
		return "redirect:/admin";

	}

}
