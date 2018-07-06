package com.vvicey.workflow.service;

import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.user.tempEntity.ActivityInternal;
import com.vvicey.workflow.entity.ActivityApprovalRequest;

import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-7-2 下午1:55
 * @Description 考试申请审批工作流抽象类
 */
public interface ActivityApprovalRequestService {

    ActivityInternal createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest, int uid);

    ActivityInternal updateRequest(String taskId, ExaminationInternal examinationInternal);

    void deleteRequest(String taskId);

    void approve(Map<String, Object> statusAndExaminationExternal);

    List<ActivityInternal> queryListRequest(int uid);

    List<ActivityInternal> approvalQueryList();
}
