package com.vvicey.login.entity;

import com.vvicey.permission.entity.Role;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-24 下午7:21
 * @Description 登陆者实体类
 */
public class Loginer {

    private Integer uId;//登陆者id
    private String uName;//登陆者姓名
    private String uPassword;//登陆者密码
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

    public String getUname() {
        return uName;
    }

    public void setUname(String uName) {
        this.uName = uName == null ? null : uName.trim();
    }

    public String getUpassword() {
        return uPassword;
    }

    public void setUpassword(String uPassword) {
        this.uPassword = uPassword == null ? null : uPassword.trim();
    }
}