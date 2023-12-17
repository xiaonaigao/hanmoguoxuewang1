package com.wzl.service;

import com.wzl.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 *
 */
public interface TypeService {
//    保存分类
    Type saveType(Type type);
//    根据主键查询分类
    Type getType(Long id);
//    分类分页查询
    Page<Type> listType(Pageable pageable);
    //在文章编辑页面获取标签列表
    List<Type> listType();
    //获取主页显示的分类的条数
    List<Type> listTypeTop(Integer size);
//    修改分类，通过id查询到相应的分类
    Type updateType(Long id,Type type);
//    删除分类
    void deleteType(Long id);
    //通过名称查找分类
    Type getTypeByName(String name);

}
