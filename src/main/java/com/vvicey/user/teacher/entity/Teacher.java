package com.vvicey.user.teacher.entity;

/**
 * @Author nana
 * @Date 18-6-30 下午4:54
 * @Description 教师实体类
 */
public class Teacher {

    /**
     * 教师id
     */
    private Integer tid;
    /**
     * 教师姓名
     */
    private String name;
    /**
     * 教师编号
     */
    private Integer teacherNumber;
    /**
     * 教师专业
     */
    private Integer major;
    /**
     * 教师学院
     */
    private Integer institute;
    /**
     * 教师手机号码
     */
    private Integer phone;
    /**
     * 教师的登陆id
     */
    private Integer uid;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(Integer teacherNumber) {
        this.teacherNumber = teacherNumber;
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