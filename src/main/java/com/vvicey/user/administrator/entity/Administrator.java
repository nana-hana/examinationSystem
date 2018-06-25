package com.vvicey.user.administrator.entity;

/**
 * @Author nana
 * @Date 18-6-25 下午3:46
 * @Description 管理员实体类
 */
public class Administrator {

    private Integer aid;//管理员id
    private String aname;//管理员名字
    private Integer ainstitute;//管理员学院
    private Integer aphone;//管理员手机号码
    private Integer uid;//管理员的登陆id

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname == null ? null : aname.trim();
    }

    public Integer getAinstitute() {
        return ainstitute;
    }

    public void setAinstitute(Integer ainstitute) {
        this.ainstitute = ainstitute;
    }

    public Integer getAphone() {
        return aphone;
    }

    public void setAphone(Integer aphone) {
        this.aphone = aphone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}