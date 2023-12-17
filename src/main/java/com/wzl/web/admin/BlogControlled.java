package com.wzl.web.admin;

import com.wzl.po.Blog;
import com.wzl.po.User;
import com.wzl.service.BlogService;
import com.wzl.service.TagService;
import com.wzl.service.TypeService;
import com.wzl.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @version 1.0
 * 博客登录控制器
 * 就是必须登录才能访问后面的页面
 */
@Controller
@RequestMapping("/admin")
public class BlogControlled {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
   //返回到文章列表页面，进行分页查询，根据model.addAttribute进行数据渲染。
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }
    //根据后台组合文章进行查询，BlogQuery条件是标题+分类+推荐进行搜索,根据model.addAttribute进行数据渲染。
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }
//点击发布按钮,跳转到发布文章的发布页面,
    @GetMapping("/blogs/input")
    public String input(Model model) {
//        标签和分类的初始化
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    //        标签和分类的初始化,获取到所有的分类和标签
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }
//    点击编辑按钮后,
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
//        标签和分类的初始化
        setTypeAndTag(model);
//        通过URL获取到文章的id,查到对应的文章,加载到blog上
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog",blog);
        return INPUT;
    }

//发布文章保存
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

//文章删除
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }









}
