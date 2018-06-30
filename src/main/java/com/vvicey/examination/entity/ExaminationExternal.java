package com.vvicey.examination.entity;

import java.util.Date;

/**
 * @Author nana
 * @Date 18-6-30 下午11:21
 * @Description 考试外在因素(时间地点人)实体类
 */
public class ExaminationExternal {

    private Integer eeid;//examinationExternal的id
    private Date examTime;//考试开始的时间
    private String examPlace;//考试地点
    private Integer teacherNumber;//监考老师编号
    private Integer institute;//归属于哪个学院管理员管理
    private Integer eiid;//考试试卷id

    public Integer getEeid() {
        return eeid;
    }

    public void setEeid(Integer eeid) {
        this.eeid = eeid;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace == null ? null : examPlace.trim();
    }

    public Integer getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(Integer teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public Integer getInstitute() {
        return institute;
    }

    public void setInstitute(Integer institute) {
        this.institute = institute;
    }

    public Integer getEiid() {
        return eiid;
    }

    public void setEiid(Integer eiid) {
        this.eiid = eiid;
    }
}