package com.vvicey.user.login.entity;

import com.vvicey.permission.entity.Role;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-24 下午7:21
 * @Description 登陆者实体类
 */
public class Loginer {

    private Integer uId;//登陆者id
    private String username;//登陆者姓名
    private String password;//登陆者密码
    private List<Role> roleList;//角色集

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getUid() {
        return uId;
    }

    public void setUid(Integer uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}