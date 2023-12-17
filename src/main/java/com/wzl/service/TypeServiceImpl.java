package com.wzl.service;

import com.wzl.NotFoundException;
import com.wzl.dao.TypeRepository;
import com.wzl.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 * 实现分类操作的方法
 */
@Service
public class TypeServiceImpl implements TypeService{
//    数据库操作
    @Autowired
    private TypeRepository typeRepository;
    @Transactional//增删改查都写在事务里面
    @Override
    //    保存分类，直接调用Repoistory里面的save()方法。
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
//    通过id查到分类
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }
    @Transactional
    @Override
//    分类的分页查询
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
//后台获取所有的分类
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
     //按大小排序在首页前端展示
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    //    修改分类
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        //先查询到相应的对象
        Type t = typeRepository.findOne(id);
        //没有查询到相应的分类
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        //把修改后的type复制到查询到的t里面
        BeanUtils.copyProperties(type,t);
        //t对象有id进行更新的动作
        return typeRepository.save(t);
    }


    @Transactional
    @Override
//    根据一个id删除分类
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }

    @Override
//    根据分类名称查询分类
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
}
