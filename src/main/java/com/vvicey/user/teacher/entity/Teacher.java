package com.vvicey.user.teacher.entity;

/**
 * @Author nana
 * @Date 18-6-25 下午3:49
 * @Description 教师实体类
 */
public class Teacher {

    private Integer tid;//教师id
    private String tname;//教师姓名
    private Integer major;//教师专业
    private Integer institute;//教师学院
    private Integer tphone;//教师手机号码
    private Integer uid;//教师的登陆id

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getInstitute() {
        return institute;
    }

    public void setInstitute(Integer institute) {
        this.institute = institute;
    }

    public Integer getTphone() {
        return tphone;
    }

    public void setTphone(Integer tphone) {
        this.tphone = tphone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}