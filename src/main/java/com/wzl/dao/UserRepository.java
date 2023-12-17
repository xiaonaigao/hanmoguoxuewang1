package com.wzl.dao;

import com.wzl.po.Type;
import com.wzl.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
//继承后能够增删改查,不管细节可以操作数据库
public interface UserRepository extends JpaRepository<User,Long> {
//    根据用户名和密码查询数据库
    User findByUsernameAndPassword(String username,String password);
    //根据用户名查找
    User findByUsername(String username);
    Page<User> findAll(Specification<User> username, Pageable pageable);
}
