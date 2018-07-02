package com.vvicey.workflow.service;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.task.BeginExamination;
import com.vvicey.common.utils.QuartzCronDateUtils;
import com.vvicey.common.utils.QuartzManagerUtils;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.workflow.dao.ActivityApprovalRequestMapper;
import com.vvicey.workflow.entity.ActivityApprovalRequest;
import org.activiti.engine.task.Task;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-7-2 下午1:56
 * @Description 考试申请审批工作流实现类
 */
@Service("ActivityApprovalRequestServiceImpl")
public class ActivityApprovalRequestServiceImpl implements ActivityApprovalRequestService {

    private static final java.lang.String APPROVAL_REQUEST_FLOW_ID = "approval_request";//bpmn表id

    /**
     * 申请状态
     */
    private static final int APPROVAL_REQUEST_STATUS_PSSS = 1;//通过
    private static final int APPROVAL_REQUEST_STATUS_REFUSE = 2;//拒绝

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActivityApprovalRequestMapper activityApprovalRequestMapper;
    @Autowired
    private ExaminationInternalService examinationInternalService;
    @Autowired
    private ExaminationExternalService examinationExternalService;

    /**
     * 工作流申请创建考试事件
     *
     * @param examinationInternal 考试事件具体信息
     */
    @Override
    @Transactional
    public void createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest) {
        examinationInternalService.createExamination(examinationInternal);
        activityApprovalRequest.setEiid(examinationInternal.getEiid());
        activityApprovalRequestMapper.insertSelective(activityApprovalRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("activity", activityApprovalRequest);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(APPROVAL_REQUEST_FLOW_ID);
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.complete(task.getId(), map);
    }

    /**
     * 工作流申请删除考试事件
     *
     * @param eiid 根据考试事件id删除
     */
    @Override
    @Transactional
    public void deleteRequest(int eiid) {

    }

    /**
     * 工作流申请修改考试事件(根据考试事件id查找)
     *
     * @param examinationInternal 考试事件修改的具体信息
     */
    @Override
    @Transactional
    public void updateRequest(ExaminationInternal examinationInternal) {
    }

    /**
     * 进行审批
     *
     * @param statusAndExaminationExternal 接受考试外在因素信息,接受审批信息类
     */
    @Override
    @Transactional
    public void approveCreate(Map<String, Object> statusAndExaminationExternal) {
        int status = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal.get("status")),
                Integer.class);
        String taskId = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal.get("taskId")),
                String.class);
        int eiid = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal.get("eiid")),
                Integer.class);
        Map<String, Object> variable = taskService.getVariables(taskId);
        ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) variable.get("activity");
        if (status == APPROVAL_REQUEST_STATUS_PSSS) {
            activityApprovalRequest.setStatus(APPROVAL_REQUEST_STATUS_PSSS);
            activityApprovalRequestMapper.updateByEiidSelective(activityApprovalRequest);
            ExaminationExternal examinationExternal = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal
                    .get("examinationExternal")), ExaminationExternal.class);
            examinationExternal.setEiid(eiid);
            examinationExternalService.createExaminationExternal(examinationExternal);
            ExaminationInternal examinationInternal = examinationInternalService.queryExaminationInternalByEiid(eiid);
            String subject = examinationInternal.getSubjectId().toString();
            String institute = examinationExternal.getInstitute().toString();
            String time = QuartzCronDateUtils.getCron(examinationExternal.getExamTime());
            int paperKind = examinationInternal.getPaperKind();
            QuartzManagerUtils.addJob(subject + "考试", institute, BeginExamination.class, time, paperKind);
        } else {
            activityApprovalRequest.setStatus(APPROVAL_REQUEST_STATUS_REFUSE);
            activityApprovalRequestMapper.updateByEiidSelective(activityApprovalRequest);
        }
        taskService.complete(taskId);
    }

    @Override
    @Transactional
    public void approveDelete() {
    }

    @Override
    @Transactional
    public void approveUpdate() {
    }

    /**
     * 审批者查询需要审批的申请
     *
     * @param username 审批者账号
     * @return 返回需要审批的申请list
     */
    @Override
    public List<Map<String, Object>> approvalQueryList(String username) {
        List<Map<String, Object>> requestList = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().processVariableValueEquals(username).list();
        //转换成页面实体
        if (CollectionUtils.isNotEmpty(taskList)) {
            for (Task task : taskList) {
                Map<String, Object> variable = taskService.getVariables(task.getId());
                ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) variable.get("activity");
                activityApprovalRequest = activityApprovalRequestMapper.selectByEiid(activityApprovalRequest.getEiid());
                activityApprovalRequest.setTaskId(task.getId());
                ExaminationInternal examinationInternal = examinationInternalService.queryExaminationInternalByEiid(
                        activityApprovalRequest.getEiid());
                variable.put("activity", activityApprovalRequest);
                variable.put("examinationInternal", examinationInternal);
                requestList.add(variable);
            }
        }
        return requestList;
    }

    @Override
    public List<Map<String, Object>> queryListRequest() {
        return null;
    }
}
