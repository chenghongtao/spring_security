package com.cht.spring.security.service;

import com.cht.spring.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    //注册用户
    void insertUser(User user);

    //更新用户信息
    int updateInfo(User user);
}
