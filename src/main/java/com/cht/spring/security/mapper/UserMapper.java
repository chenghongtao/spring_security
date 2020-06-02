package com.cht.spring.security.mapper;

import com.cht.spring.security.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User loadUserByUsername(@Param("userName") String userName);

    int insertUser(User user);
}
