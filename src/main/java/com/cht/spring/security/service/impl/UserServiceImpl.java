package com.cht.spring.security.service.impl;

import com.cht.spring.security.mapper.RoleMapper;
import com.cht.spring.security.mapper.UserMapper;
import com.cht.spring.security.model.User;
import com.cht.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库中根据名称查询用户
        User user=userMapper.loadUserByUsername(username);
        if(user==null){
            throw new  RuntimeException(username+" not exists");
        }
        return user;
    }

    @Override
    //如果产生异常，就回滚
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(User user) {

        //当插入用户成功的时候，会返回自己的id
        int userId=userMapper.insertUser(user);

        if(!user.getRoles().isEmpty()) {

            //给每个角色赋值userid属性
            user.getRoles().forEach(ele -> {
                ele.setUserId(userId);
            });

            roleMapper.batchInsetRole(user.getRoles());
        }


    }
}
