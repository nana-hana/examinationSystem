package com.vvicey.user.tempEntity;

/**
 * @Author nana
 * @Date 18-8-30 上午10:47
 * @Description 超级管理员个人信息及超级管理员登陆信息
 */
public class SuperAdministratorLoginer {

    private int uid;//uid
    private String username;//账号
    private String password;//密码
    private Integer said;//超级管理员id
    private String name;//管理员名字
    private Integer phone;//管理员手机号码

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
