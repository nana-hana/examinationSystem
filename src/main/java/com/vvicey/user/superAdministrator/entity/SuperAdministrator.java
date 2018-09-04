package com.vvicey.user.superAdministrator.entity;

/**
 * @Author nana
 * @Date 18-8-30 上午11:03
 * @Description 超级管理员实体类
 */
public class SuperAdministrator {

    private Integer said;//超级管理员id
    private String name;//超级管理员名字
    private Integer phone;//超级管理员手机号码
    private Integer uid;//超级管理员的登陆id

    public Integer getSaid() {
        return said;
    }

    public void setSaid(Integer said) {
        this.said = said;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}