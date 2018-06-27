package com.vvicey.user.student.entity;

/**
 * @Author nana
 * @Date 18-6-25 下午3:47
 * @Description 学生实体类
 */
public class Student {

    private Integer sid;//学生id
    private String sname;//学生姓名
    private Integer studentNumber;//学生学号
    private Integer studentClass;//学生班级
    private Integer major;//学生专业
    private Integer institute;//学生学院
    private Integer sphone;//学生手机号码
    private Integer uid;//学生的登陆id

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
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

    public Integer getSphone() {
        return sphone;
    }

    public void setSphone(Integer sphone) {
        this.sphone = sphone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}