package com.wzl.web.admin;

import com.wzl.po.Type;
import com.wzl.service.TypeService;
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
public class TypeController {
    //首先注入Service
    @Autowired
    private TypeService typeService;
    /*** 查看分类列表*/
    @GetMapping("/types")
    //    Model model前端页面模型展示
    //@PageableDefault通过注解给pageable一些默认的值
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
    //    前端页面展示模型，把查询出来的链表给page
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    /** * 添加的分类接口*/
    @GetMapping("/types/input")
    public String input(Model model){
    //    model.addattribute(K,V)往前台传数据，可以传对象
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    /*** 修改分类的接口*/
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
//      通过id查找到的type名称传入到前端
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    /** * 添加数据的提交*/
//    RedirectAttributes通过这个获取消息提示
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        // 通过添加的分类进行查找,如果不为空说明已经存在
        Type type1=typeService.getTypeByName(type.getName());
        if(type1 !=null){
            result.rejectValue("name","nameError","该分类已存在，不能重复添加");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        //进行标签的添加，调用saveType()的方法
       Type t= typeService.saveType(type);
       if (t == null){
           attributes.addFlashAttribute("message","增加失败");
       }else{
           attributes.addFlashAttribute("message","增加成功");

       }
       return "redirect:/admin/types";
    }
    //编辑分类的方法.
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
//       通过分类名称获取数据库是否存在该分类.
        Type type1=typeService.getTypeByName(type.getName());
        if(type1 !=null){
            result.rejectValue("name","nameError","该分类已存在，不能重复添加");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
//      否则就进行添加分类
        Type t= typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");

        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
    //通过分类ID删除分类
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
