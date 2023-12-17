package com.wzl.web;

import com.wzl.po.Type;
import com.wzl.service.BlogService;
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

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
@Controller
public class TypeShowController {
    //首先注入Service
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    /*** 查看分类列表
     * 定义types方法，返回types页面。用@GetMapping("/types/{id}"）
     * 去请求当前活跃的types的id，拿到分类，不知道分类值是多少就传递一个-1，就说明是从首页地方点进来的，
     * */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
