package com.vvicey.examination.entity;

/**
 * @Author nana
 * @Date 18-6-30 下午4:54
 * @Description 考试内在因素(试卷生成)实体类
 */
public class ExaminationInternal {

    private Integer eiid;//examinationInternal的id
    private Integer singleNumber;//单选题数量
    private Integer singleScore;//单选题总分值
    private Integer multipleNumber;//多选题数量
    private Integer multipleScore;//多选题总分值
    private Integer checkingNumber;//判断题数量
    private Integer checkingScore;//判断题总分值
    private Integer paperLevel;//试卷平均难度
    private Integer paperKind;//试卷生成类型(0:a、b卷型，1:全随机型)
    private Integer subjectId;//考试科目id
    private Integer submitTeacherNumber;//提交考试申请的老师编号
    private Integer studentClass;//参加考试的班级

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

    public Integer getSubmitTeacherNumber() {
        return submitTeacherNumber;
    }

    public void setSubmitTeacherNumber(Integer submitTeacherNumber) {
        this.submitTeacherNumber = submitTeacherNumber;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }
}