package com.vvicey.workflow.service;

import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.workflow.entity.ActivityApprovalRequest;

import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-7-2 下午1:55
 * @Description 考试申请审批工作流抽象类
 */
public interface ActivityApprovalRequestService {

    void createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest);

    void updateRequest(int taskId, ExaminationInternal examinationInternal);

    void approve(Map<String, Object> statusAndExaminationExternal);

    List<Map<String, Object>> approvalQueryList(String username);

    List<Map<String, Object>> queryListRequest(String username);
}
