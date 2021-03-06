package com.cht.spring.security;

import com.cht.spring.security.mapper.UserMapper;
import com.cht.spring.security.model.Role;
import com.cht.spring.security.model.User;
import com.cht.spring.security.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringSecurityApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void contextLoads() {

        User u1 = new User();
        u1.setName("chenghongtao");
        u1.setPasswd(encoder.encode("chenghongtao"));
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);

        List<Role> rs1 = new ArrayList<>();
        Role r1 = new Role();
        r1.setRoleNameCn("管理员");
        r1.setRoleNameZn("ROLE_admin");
        rs1.add(r1);
        u1.setRoles(rs1);

        //保存到数据库
        //userDao.save(u1);
        service.insertUser(u1);

        User u2 = new User();
        u2.setPasswd(encoder.encode("test"));
        u2.setName("test");
        u2.setAccountNonExpired(true);
        u2.setAccountNonLocked(true);
        u2.setCredentialsNonExpired(true);
        u2.setEnabled(true);
        List<Role> rs2 = new ArrayList<>();
        Role r2 = new Role();
        r2.setRoleNameZn("ROLE_user");
        r2.setRoleNameCn("普通用户");
        rs2.add(r2);
        u2.setRoles(rs2);

        service.insertUser(u2);
    }

}
