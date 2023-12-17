package com.wzl.service;

import com.wzl.NotFoundException;
import com.wzl.dao.UserRepository;
import com.wzl.po.User;
import com.wzl.util.MD5Utils;
import com.wzl.vo.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//  登录 通过用户名和密码检查来是否正确
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    //注册
    @Transactional
    @Override
    public User saveUser(User user) {
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setAvatar("/images/touxiang2.jpg");
        user.setType(2);
        return userRepository.save(user);
    }

    //根据用户名查找
    @Override
    public User checkUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    //管理员查询页面展示
    @Transactional
    @Override
    public Page<User> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }

    //删除用户
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    //修改用户
    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userRepository.findOne(id);
        if (u == null) {
            throw new NotFoundException("不存在该用户");
        }
        Date createTime = u.getCreateTime();
        //把修改后的user复制到查询u里面去
        BeanUtils.copyProperties(user, u);
        String password=user.getPassword();
        if (password.length()!=32){
            u.setPassword(MD5Utils.code(password));
        }
        u.setUpdateTime(new Date());
        u.setType(2);
        u.setCreateTime(createTime);
        u.setAvatar("/images/touxiang2.jpg");
        return userRepository.save(u);
    }

    //查找用户
    @Transactional
    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page<User> listUser(Pageable pageable, UserQuery user) {
        return userRepository.findAll(new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(user.getUsername()) && user.getUsername() != null) {
                    predicates.add(cb.like(root.<String>get("username"), "%" + user.getUsername() + "%"));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

}
