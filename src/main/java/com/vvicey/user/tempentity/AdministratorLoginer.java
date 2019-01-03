package com.vvicey.user.tempentity;

/**
 * @Author nana
 * @Date 18-7-5 下午5:51
 * @Description 管理员账号个人信息实体类
 */
public class AdministratorLoginer {

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
     * 管理员id
     */
    private Integer aid;
    /**
     * 管理员名字
     */
    private String name;
    /**
     * 管理员学院
     */
    private Integer institute;
    /**
     * 管理员手机号码
     */
    private Integer phone;

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
}