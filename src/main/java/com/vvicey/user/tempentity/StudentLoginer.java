package com.vvicey.user.tempentity;

/**
 * @Author nana
 * @Date 18-7-3 下午11:03
 * @Description 学生账号个人信息实体类
 */
public class StudentLoginer {

    /**
     * uid
     */
    private int uid;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 学号
     */
    private int studentNumber;
    /**
     * 班级
     */
    private int studentClass;
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
     * 姓名
     */
    private String name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(int studentClass) {
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
}
