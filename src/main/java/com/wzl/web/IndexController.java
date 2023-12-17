package com.wzl.web;

import com.wzl.NotFoundException;
import com.wzl.service.BlogService;
import com.wzl.service.TagService;
import com.wzl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wang
 * @version 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    //通过model.addAttribute往首页传对象列表，
    //其中有古文列表，朝代诗人等标签列表，古文属性分类列表，推荐文章列表加载在首页里面。
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(12));
        model.addAttribute("recommenedBlogs", blogService.listRecommenedBlogTop(6));
        return "index";
    }
    @GetMapping("/index")
    public String index1(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommenedBlogs", blogService.listRecommenedBlogTop(6));
        return "index";
    }
    //通过前端页面搜索框获取到搜索的关键词@RequestParam String query，
    // 通过blogService.listBlog()方法进行搜索，通过model.addAttribute往首页传查到的对象列表
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
//        通过blogService.listBlog  ("%"+query+"%", pageable))把搜索的内容给 page
        model.addAttribute("page", blogService.listBlog  ("%"+query+"%", pageable));
        // 在搜索框里加载出 搜索的关键词
        model.addAttribute("query", query);
        return "search";
    }

//通过文章id来获取文章详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommenedBlogTop(3));
        return "index :: newblogList";
    }
    //在标签和分类能够登录
    @GetMapping("/tags/user/login")
    public String l1() {
        return "user/login";
    }
    @GetMapping("/types/user/login")
    public String l2() {
        return "user/login";
    }
    @GetMapping("/blog/user/login")
    public String l3() {
        return "user/login";
    }
    //跳转注册页面
    @GetMapping("/types/user/userRegister")
    public String r1() {
        return "user/userRegister";
    }
    @GetMapping("/tags/user/userRegister")
    public String r2() {
        return "user/userRegister";
    }

}
