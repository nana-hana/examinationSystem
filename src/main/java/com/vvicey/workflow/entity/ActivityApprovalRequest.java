package com.vvicey.workflow.entity;

import java.io.Serializable;

/**
 * @Author nana
 * @Date 18-7-2 下午3:04
 * @Description 考试申请审批信息实体类
 */
public class ActivityApprovalRequest implements Serializable {

    private static final long serialVersionUID = -3173729191432597839L;

    /**
     * id
     */
    private Integer id;
    /**
     * 审批状态
     */
    private Integer status;
    /**
     * 申请老师编号
     */
    private Integer submitTeacherNumber;
    /**
     * 申请信息
     */
    private String comments;
    /**
     * 相关考试事件编号
     */
    private Integer eiid;
    /**
     * 试卷任务id
     */
    private String taskId;

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
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getEiid() {
        return eiid;
    }

    public void setEiid(Integer eiid) {
        this.eiid = eiid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}