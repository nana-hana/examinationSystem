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

    void deleteRequest(int eiid);

    void updateRequest(ExaminationInternal examinationInternal);

    void approveCreate(Map<String, Object> statusAndExaminationExternal);

    void approveDelete();

    void approveUpdate();

    List<Map<String, Object>> approvalQueryList(String username);

    List<Map<String, Object>> queryListRequest();
}
