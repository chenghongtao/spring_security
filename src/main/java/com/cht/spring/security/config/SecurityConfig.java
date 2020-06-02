package com.cht.spring.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Writer;

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

        //前后端不分离的情况，主要是在登录成功或者失败后，通过服务端返回的json信息，由前端进行页面的跳转
//         http
//         .authorizeRequests()
//                 //所有的请求都需要经过验证
//                 .anyRequest().authenticated()
//                 .and()
//                 //表单配置
//                 .formLogin()
//                 //配置登录页面，同时也是配置了登录接口也是/login.html，登录页面是get请求，登录接口是post请求
//                 .loginPage("/login_my.html")
//                 //也可以指定单独的登录接口
//                 .loginProcessingUrl("/login/doLogin")
//                 //指定登录用户名参数为name，默认为username
//                 .usernameParameter("name")
//                 //指定密码参数名称为passwd，默认为passowrd
//                 .passwordParameter("passwd")
//                 //登陆成功转发的路径
//                 //.successForwardUrl("/login/success")
//                 //登录成功，重定向到某个页面，和successForwardUrl只用定义一个
//                 .defaultSuccessUrl("/login/redirect")
//                 //定义失败转发的url
//                 //.failureForwardUrl("/login/fail")
//                 //定义失败重定向的url,和failureForwardUrl，定义一个即可
//                 .failureUrl("/login/failredirect")
//                 //表示上个and之后定义的不需要拦截, 登录相关的页面不要拦截
//                 .permitAll()
//                 .and()
//                 //定义退出相关信息
//                 .logout()
//                //定义退出路径为logout，默认为logout，也可自己指定，注销登录的请求是get请求
//                .logoutUrl("/security/logout")
//                //如果是post请求，则需要设置以下内容
//                //.logoutRequestMatcher(new RegexRequestMatcher("/security/logout","POST"))
//                //指定注销成功跳转的路径为登录页
//                .logoutSuccessUrl("/login_my.html")
//                //让session失效，默认为true
//                .invalidateHttpSession(true)
//                //清除认证信息,默认也是清除的
//                .clearAuthentication(true)
//                .permitAll()
//                .and()
//                //关闭csrf
//                .csrf().disable();




        //前后端分离的情况使用successHandler和failHandler
                 http
         .authorizeRequests()
                  //admin接口访问需要admin权限，但是admin权限包含user权限
                  .antMatchers("/hello/admin/**").hasRole("admin")
                 //user接口访问需要user权限
                 .antMatchers("/hello/user/**").hasRole("user")
                 //所有的请求都需要经过验证
                 .anyRequest().authenticated()
                 .and()
                 //表单配置
                 .formLogin()
                 //配置登录页面，同时也是配置了登录接口也是/login.html，登录页面是get请求，登录接口是post请求
                 .loginPage("/login_my.html")
                 //也可以指定单独的登录接口
                 .loginProcessingUrl("/login/doLogin")
                 //指定登录用户名参数为name，默认为username
                 .usernameParameter("name")
                 //指定密码参数名称为passwd，默认为passowrd
                 .passwordParameter("passwd")


                 //登陆成功后给前端返回的数据
                 .successHandler((request,response,authentication)->{
                     System.out.println("-------------------------"+authentication.getPrincipal());
                     response.setContentType("application/json;charset=UTF-8");
                     Writer out=response.getWriter();
                     out.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                     out.flush();
                     out.close();
                 })


                 //登陆失败后给前端返回的数据
                 .failureHandler((request,response,exception)->{
                     System.out.println("-------------------------"+exception.getClass().getName());
                     String errMsg="";
                     if(exception instanceof BadCredentialsException){
                         errMsg="用户名或者密码不正确";
                     }

                     if(exception instanceof LockedException){
                         errMsg="账户被锁定";
                     }

                     if(exception instanceof CredentialsExpiredException){
                         errMsg="密码过期";
                     }

                     if(exception instanceof AccountExpiredException){
                         errMsg="账户过期";
                     }

                     if(exception instanceof DisabledException){
                         errMsg="账户被禁用";
                     }

                     response.setContentType("application/json;charset=UTF-8");
                     Writer out=response.getWriter();
                     out.write("login fail "+errMsg);
                     out.flush();
                     out.close();
                 })
                 //表示上个and之后定义的不需要拦截, 登录相关的页面不要拦截
                 .permitAll()
                 .and()
                 //定义退出相关信息
                 .logout()
                //定义退出路径为/out,但是访问路径为http://localhost:8888/security/out，默认为logout，也可自己指定，注销登录的请求是get请求
                .logoutUrl("/out")

                //定义退出成功后的，给前端返回数据
                .logoutSuccessHandler((request,response,authentication) ->{
                    response.setContentType("application/json;charset=UTF-8");
                    Writer out=response.getWriter();
                    out.write("logout success ");
                    out.flush();
                    out.close();
                })
                //让session失效，默认为true
                .invalidateHttpSession(true)
                //清除认证信息,默认也是清除的
                .clearAuthentication(true)
                .permitAll()
                .and()
                //关闭csrf
                .csrf().disable().
                //处理未登录时，用户访问到需要登录才能访问的数据，需要将用户引导到登录页面，但是后端只是给前端返回json，则需要返回固定内容的json
                //如果不添加以下代码，则没有登录，直接跳转到登录页面
                exceptionHandling().authenticationEntryPoint((request,response,authException)->{
                     response.setContentType("application/json;charset=UTF-8");
                     Writer out=response.getWriter();
                     out.write("Not logged in yet, please log in first ");
                     out.flush();
                     out.close();
                 });

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


    /**
     * 配置角色继承关系，有了这个配置，admin才能是最大用户
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
}
