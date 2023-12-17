package com.wzl.web;

import com.wzl.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wang
 * @version 1.0
 */
@Controller
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;
    //归档就是把我们的博客按照年份进行整理归档展示，一共多少条博客，
    // 然后按照年份，比如17年有多少条，依次列出，归档实际上就是对blog表的_blog数据库进行归档，首先要筛选出来有几个年份就有几个list
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
