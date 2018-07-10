package com.vvicey.user.tempEntity;

/**
 * @Author nana
 * @Date 18-7-5 下午9:53
 * @Description 试卷及提交老师实体类
 */
public class ActivityInternal {

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
    private Integer studentClass;//参加考试的班级
    private Integer examinationTime;//考试持续时间

    private Integer id;//id
    private Integer status;//审批状态
    private Integer submitTeacherNumber;//申请老师编号
    private String comments;//申请信息
    private String taskId;//试卷任务id

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubmitTeacherNumber() {
        return submitTeacherNumber;
    }

    public void setSubmitTeacherNumber(Integer submitTeacherNumber) {
        this.submitTeacherNumber = submitTeacherNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
