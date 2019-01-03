package com.vvicey.user.student.entity;

/**
 * @Author nana
 * @Date 18-6-25 下午3:47
 * @Description 学生实体类
 */
public class Student {

    /**
     * 学生id
     */
    private Integer sid;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生学号
     */
    private Integer studentNumber;
    /**
     * 学生班级
     */
    private Integer studentClass;
    /**
     * 学生专业
     */
    private Integer major;
    /**
     * 学生学院
     */
    private Integer institute;
    /**
     * 学生手机号码
     */
    private Integer phone;
    /**
     * 学生的登陆id
     */
    private Integer uid;
    /**
     * 学院名字
     */
    private String instituteName;

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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