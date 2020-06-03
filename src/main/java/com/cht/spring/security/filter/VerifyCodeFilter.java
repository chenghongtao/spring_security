package com.cht.spring.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;

@Component
public class VerifyCodeFilter extends GenericFilterBean {

    private String defaultFilterProcessUrl = "/login/doLogin";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        //当请求路径是doLogin并且是post请求时
        if ("POST".equals(req.getMethod()) && defaultFilterProcessUrl.equals(req.getServletPath())) {

            String code = req.getParameter("code");
            String sessionCode = req.getSession().getAttribute("code").toString();

            if (StringUtils.isEmpty(code)) {
                response.setContentType("application/json;chartset=UTF-8");
                Writer out=response.getWriter();
                out.write("verify code is null");
                out.close();
                throw new AuthenticationServiceException("验证码不能为空");
            }


            if (!code.equals(sessionCode)) {
                response.setContentType("application/json;chartset=UTF-8");
                Writer out=response.getWriter();
                out.write("verify code error");
                out.close();
                throw new AuthenticationServiceException("验证码错误");
            }

        }

        chain.doFilter(request, response);

    }
}
