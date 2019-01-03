package com.vvicey.workflow.service;

import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.user.tempentity.ActivityInternal;
import com.vvicey.workflow.entity.ActivityApprovalRequest;

import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-7-2 下午1:55
 * @Description 考试申请审批工作流抽象类
 */
public interface ActivityApprovalRequestService {

    /**
     * 工作流申请创建考试事件
     *
     * @param examinationInternal     考试事件具体信息
     * @param activityApprovalRequest 申请状态信息
     * @param uid                     uid
     * @return 返回
     */
    ActivityInternal createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest, int uid);

    /**
     * 工作流申请修改考试事件(根据考试事件id查找)
     *
     * @param taskId              需要修改的任务的taskId
     * @param examinationInternal 考试事件修改的具体信息
     * @return 返回
     */
    ActivityInternal updateRequest(String taskId, ExaminationInternal examinationInternal);

    /**
     * 根据taskId删除考试申请
     *
     * @param taskId 任务id
     */
    void deleteRequest(String taskId);

    /**
     * 进行审批创建
     *
     * @param statusAndExaminationExternal 接受考试外在因素信息,接受审批信息类
     */
    void approve(Map<String, Object> statusAndExaminationExternal);

    /**
     * 根据老师uid查询申请的任务
     *
     * @param uid 登陆者老师uid
     * @return 返回申请的任务
     */
    List<ActivityInternal> queryListRequest(int uid);

    /**
     * 审批者查询需要审批的申请(获取所有申请)
     *
     * @return 返回需要审批的申请list
     */
    List<ActivityInternal> approvalQueryList();
}
