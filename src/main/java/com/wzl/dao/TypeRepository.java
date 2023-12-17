package com.wzl.dao;

import com.wzl.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 * 分类管理的数据库
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
   //自定义通过名字进行查找
   Type findByName(String name);
  //获取首页展示的标签
   @Query("select t from Type t")
   List<Type> findTop(Pageable pageable);

}
