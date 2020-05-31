package com.cht.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
   // private VerifyCodeFilter verifyCodeFilter;

    /**
     * 在java代码中配置用户名和密码
     * chenghongtao的明文密码是chenghongtao
     * test用户的明文密码是test
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("chenghongtao").roles("admin").password("$2a$10$5XnPBo0hqCNNO.0YaTxq5OZH7chsjUj/UDKb9y5aPHBey9FIGlIH2").
                and().withUser("test").roles("user").password("$2a$10$InXN1Gcf2ESkhLGQU.WdLeXuyz53k29qY3MGdh/1K/gE0u1bFVyeG");
    }

    /**
     * 定义密码编码器  把明文输出为密文
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
         .authorizeRequests()
                 //所有的请求都需要经过验证
                 .anyRequest().authenticated()
                 .and()
                 //表单配置
                 .formLogin()
                 //配置登录页面
                 .loginPage("/login.html")
                 //表示上个and之后定义的不需要拦截, 登录相关的页面不要拦截
                 .permitAll()
                 .and()
                 //关闭csrf
                 .csrf().disable();
    }

    /**
     * 忽略拦截以/hello开头的所有请求
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        //web.ignoring().antMatchers("/hello/*");
        //一般对于静态文件,可以不让走过滤器
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }
}
