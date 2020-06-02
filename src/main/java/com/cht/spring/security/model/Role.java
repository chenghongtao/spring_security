package com.cht.spring.security.model;

/**
 * 角色实体类
 */
public class Role {

    /**
     * 主键
     */
    private int roleId;

    /**
     * 角色中文名称
     */
    private String roleNameCn;

    /**
     * 角色英文名称
     */
    private String roleNameZn;

    /**
     * 用户id，作为外键
     */
    private int userId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleNameCn() {
        return roleNameCn;
    }

    public void setRoleNameCn(String roleNameCn) {
        this.roleNameCn = roleNameCn;
    }

    public String getRoleNameZn() {
        return roleNameZn;
    }

    public void setRoleNameZn(String roleNameZn) {
        this.roleNameZn = roleNameZn;
    }
}
