package com.cht.spring.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户实体类
 */
public class User implements UserDetails {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名称
     */
    private String name;


    /**
     * 用户密码（加密之后的）
     */
    private String passwd;

    /**
     * 对应多个角色
     */
    private List<Role> roles;

    /**
     * 账户是否过期
     */
    private boolean accountNonExpired;

    /**
     * 账户是否被锁
     */
    private boolean accountNonLocked;

    /**
     * 密码是否过期
     */
    private boolean credentialsNonExpired;

    /**
     * 账户是否可用
     */
    private boolean enabled;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 该方法用于返回用户角色信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> result=new ArrayList<>();
        for (Role role : roles) {
            //获取该用户所有角色信息
            result.add(new SimpleGrantedAuthority(role.getRoleNameZn()));
        }
        return result;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
