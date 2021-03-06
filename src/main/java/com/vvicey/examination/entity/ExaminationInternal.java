package com.vvicey.examination.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author nana
 * @Date 18-6-30 下午4:54
 * @Description 考试内在因素(试卷生成)实体类
 */
public class ExaminationInternal implements Serializable {

    private static final long serialVersionUID = -3173729191432597839L;

    /**
     * examinationInternal的id
     */
    private Integer eiid;
    /**
     * 单选题数量
     */
    private Integer singleNumber;
    /**
     * 单选题总分值
     */
    private Integer singleScore;
    /**
     * 多选题数量
     */
    private Integer multipleNumber;
    /**
     * 多选题总分值
     */
    private Integer multipleScore;
    /**
     * 判断题数量
     */
    private Integer checkingNumber;
    /**
     * 判断题总分值
     */
    private Integer checkingScore;
    /**
     * 试卷平均难度
     */
    private Integer paperLevel;
    /**
     * 试卷生成类型(0:a、b卷型，1:全随机型)
     */
    private Integer paperKind;
    /**
     * 考试科目id
     */
    private Integer subjectId;
    /**
     * 参加考试的班级
     */
    private Integer studentClass;
    /**
     * 考试持续时间
     */
    private Integer examinationTime;
    /**
     * 考试开始的时间
     */
    private Date examTime;

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public Integer getEiid() {
        return eiid;
    }

    public void setEiid(Integer eiid) {
        this.eiid = eiid;
    }

    public Integer getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(Integer singleNumber) {
        this.singleNumber = singleNumber;
    }

    public Integer getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(Integer singleScore) {
        this.singleScore = singleScore;
    }

    public Integer getMultipleNumber() {
        return multipleNumber;
    }

    public void setMultipleNumber(Integer multipleNumber) {
        this.multipleNumber = multipleNumber;
    }

    public Integer getMultipleScore() {
        return multipleScore;
    }

    public void setMultipleScore(Integer multipleScore) {
        this.multipleScore = multipleScore;
    }

    public Integer getCheckingNumber() {
        return checkingNumber;
    }

    public void setCheckingNumber(Integer checkingNumber) {
        this.checkingNumber = checkingNumber;
    }

    public Integer getCheckingScore() {
        return checkingScore;
    }

    public void setCheckingScore(Integer checkingScore) {
        this.checkingScore = checkingScore;
    }

    public Integer getPaperLevel() {
        return paperLevel;
    }

    public void setPaperLevel(Integer paperLevel) {
        this.paperLevel = paperLevel;
    }

    public Integer getPaperKind() {
        return paperKind;
    }

    public void setPaperKind(Integer paperKind) {
        this.paperKind = paperKind;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    public Integer getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(Integer examinationTime) {
        this.examinationTime = examinationTime;
    }

}