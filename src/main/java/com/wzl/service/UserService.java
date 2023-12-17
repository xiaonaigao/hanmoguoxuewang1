package com.wzl.service;
import com.wzl.po.User;
import com.wzl.vo.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
public interface UserService {
    //登录
    User checkUser(String username, String password);
    //注册
    User saveUser(User user );
    //用用户名查找
    User checkUsername(String username);
    //分页查找
    Page<User> listUser(Pageable pageable);
    List<User> listUser();
    //删除用户
    void deleteUser(Long id);
    //修改
    User updateUser(Long id,User user);
    //查找ID用户
    User getUser(Long id);
    //按照用户名查找用户名，管理员
    Page<User> listUser(Pageable pageable, UserQuery user);
}
