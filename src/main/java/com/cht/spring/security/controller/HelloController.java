package com.cht.spring.security.controller;

import com.cht.spring.security.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/first")
    public String hello(){
        return "hello";
    }

    @GetMapping("/test")
    public User test(User user){
        return user;
    }
}
