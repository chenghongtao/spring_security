package com.cht.spring.security.controller;

import com.cht.spring.security.model.User;
import com.cht.spring.security.util.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    //登录成功转发页面
    @RequestMapping("/success")
    public String success(){
        logger.info("login success-----------------");
        return "sucesss forward";
    }

    //登录成功重定向页面
    @RequestMapping("/redirect")
    public String redirect(){
        logger.info("login success-----------------");
        return "success redirect";
    }

    //登录失败跳转页面
    @RequestMapping("/fail")
    public String fail(){
        logger.info("login fail-----------------");
        return "fail forward";
    }


    @RequestMapping("/failredirect")
    public String failRedirect(){
        logger.info("login fail-----------------");
        return "fail redirect";
    }

    @GetMapping("/code")
    public void verifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);

        //将code保存在session中,key为code
        req.getSession().setAttribute("code",code);
        
        VerifyCodeUtils.outputImage(100, 40, resp.getOutputStream(), code);

    }
}
