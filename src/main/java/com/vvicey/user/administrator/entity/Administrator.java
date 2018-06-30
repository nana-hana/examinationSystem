package com.vvicey.user.administrator.entity;

/**
 * @Author nana
 * @Date 18-6-25 下午3:46
 * @Description 管理员实体类
 */
public class Administrator {

    private Integer aid;//管理员id
    private String name;//管理员名字
    private Integer institute;//管理员学院
    private Integer phone;//管理员手机号码
    private Integer uid;//管理员的登陆id

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getInstitute() {
        return institute;
    }

    public void setInstitute(Integer institute) {
        this.institute = institute;
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