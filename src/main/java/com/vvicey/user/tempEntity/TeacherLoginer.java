package com.vvicey.user.tempEntity;

/**
 * @Author nana
 * @Date 18-7-4 下午9:55
 * @Description 教师账号个人信息实体类
 */
public class TeacherLoginer {

    private int uid;//uid
    private String username;//账号
    private String password;//密码
    private String name;//姓名
    private int teacherNumber;//编号
    private Integer major;//学生专业
    private Integer institute;//学生学院
    private Integer phone;//学生手机号码

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(int teacherNumber) {
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
}
