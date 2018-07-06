package com.vvicey.workflow.service;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.task.BeginExamination;
import com.vvicey.common.utils.QuartzCronDateUtils;
import com.vvicey.common.utils.QuartzManagerUtils;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.user.administrator.service.AdministratorService;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempEntity.ActivityInternal;
import com.vvicey.workflow.dao.ActivityApprovalRequestMapper;
import com.vvicey.workflow.entity.ActivityApprovalRequest;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
    private TeacherService teacherService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private ActivityApprovalRequestMapper activityApprovalRequestMapper;
    @Autowired
    private ExaminationInternalService examinationInternalService;
    @Autowired
    private ExaminationExternalService examinationExternalService;


    /**
     * 工作流申请创建考试事件
     *
     * @param activityApprovalRequest 申请状态信息
     * @param examinationInternal     考试事件具体信息
     */
    @Override
    @Transactional
    public ActivityInternal createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest, int uid) {
        examinationInternalService.createExamination(examinationInternal);
        activityApprovalRequest.setEiid(examinationInternal.getEiid());
        activityApprovalRequestMapper.insertSelective(activityApprovalRequest);
        Map<String, Object> map = new HashMap<>();
        map.put("activity", activityApprovalRequest);
        map.put("teacherUid", uid);
        //给map增加条目指定受理请求的人，受理人通过processVariableValueEquals名字对申请进行审批
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(APPROVAL_REQUEST_FLOW_ID, map);
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.complete(task.getId(), map);
        return activityApprovalRequestMapper.selectByEiidAI(examinationInternal.getEiid());
    }

    /**
     * 工作流申请修改考试事件(根据考试事件id查找)
     *
     * @param taskId              需要修改的任务的taskId
     * @param examinationInternal 考试事件修改的具体信息
     */
    @Override
    @Transactional
    public ActivityInternal updateRequest(String taskId, ExaminationInternal examinationInternal) {
        Map<String, Object> map = taskService.getVariables(taskId);
        ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) map.get("activity");
        int eiid = activityApprovalRequest.getEiid();
        examinationInternal.setEiid(eiid);
        examinationInternalService.updateExaminationByEiid(examinationInternal);
        activityApprovalRequest = activityApprovalRequestMapper.selectByEiidA(eiid);
        activityApprovalRequest.setStatus(0);
        activityApprovalRequestMapper.updateByEiidSelective(activityApprovalRequest);
        map.replace("activity", activityApprovalRequest);
        taskService.setVariables(taskId, map);
        return activityApprovalRequestMapper.selectByEiidAI(activityApprovalRequest.getEiid());
    }

    /**
     * 根据老师uid查询申请的任务
     *
     * @param uid 登陆者老师uid
     * @return 返回申请的任务
     */
    @Override
    public List<ActivityInternal> queryListRequest(int uid) {
        List<ActivityInternal> requestList = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals(uid).list();
        if (CollectionUtils.isNotEmpty(tasks)) {
            for (Task task : tasks) {
                Map<String, Object> variable = taskService.getVariables(task.getId());
                ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) variable.get("activity");
                ActivityInternal activityInternal = activityApprovalRequestMapper.selectByEiidAI(activityApprovalRequest.getEiid());
                activityInternal.setTaskId(task.getId());
                requestList.add(activityInternal);
            }
        }
        List<ActivityInternal> lists = activityApprovalRequestMapper.selectByTeacherNumber
                (teacherService.queryTeacherSelf(uid).getTeacherNumber());
        for (ActivityInternal activityInternal : lists) {
            if (activityInternal.getStatus() != 0) {
                requestList.add(activityInternal);
            }
        }
        return requestList;
    }

    /**
     * 根据taskId删除考试申请
     *
     * @param taskId 任务id
     */
    @Override
    public void deleteRequest(String taskId) {
        Map<String, Object> variable = taskService.getVariables(taskId);
        ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) variable.get("activity");
        int eiid = activityApprovalRequest.getEiid();
        activityApprovalRequestMapper.deleteByEiid(eiid);
        examinationInternalService.deleteExamination(eiid);
        taskService.complete(taskId);
        taskService.deleteTask(taskId, true);
    }

    /**
     * 进行审批创建
     *
     * @param statusAndExaminationExternal 接受考试外在因素信息,接受审批信息类
     */
    @Override
    @Transactional
    public void approve(Map<String, Object> statusAndExaminationExternal) {
        int status = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal.get("status")),
                Integer.class);
        String taskId = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal.get("taskId")),
                String.class);
        ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) taskService.getVariables(taskId)
                .get("activity");
        int eiid = activityApprovalRequest.getEiid();
        if (status == APPROVAL_REQUEST_STATUS_PSSS) {
            activityApprovalRequest.setStatus(APPROVAL_REQUEST_STATUS_PSSS);
            activityApprovalRequestMapper.updateByEiidSelective(activityApprovalRequest);
            ExaminationExternal examinationExternal = examinationExternalService.queryExaminationExternalByEiid(eiid);
            if (examinationExternal == null) {
                examinationExternal = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal
                        .get("examinationExternal")), ExaminationExternal.class);
                examinationExternal.setEiid(eiid);
                examinationExternalService.createExaminationExternal(examinationExternal);
            } else {
                examinationExternal = JSON.parseObject(JSON.toJSONString(statusAndExaminationExternal
                        .get("examinationExternal")), ExaminationExternal.class);
                examinationExternal.setEiid(eiid);
                examinationExternalService.updateExaminationExternalByEeid(examinationExternal);
            }
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

    /**
     * 审批者查询需要审批的申请(获取所有申请)
     *
     * @return 返回需要审批的申请list
     */
    @Override
    public List<ActivityInternal> approvalQueryList() {
        List<ActivityInternal> approvalList = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey("approval_request_check").list();
        //转换成页面实体
        if (CollectionUtils.isNotEmpty(taskList)) {
            for (Task task : taskList) {
                ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) taskService.getVariables(task.getId()).get("activity");
                ActivityInternal activityInternal = activityApprovalRequestMapper.selectByEiidAI(activityApprovalRequest.getEiid());
                activityInternal.setTaskId(task.getId());
                approvalList.add(activityInternal);
            }
        }
        List<ActivityInternal> lists = activityApprovalRequestMapper.selectAll();
        for (ActivityInternal activityInternal : lists) {
            if (activityInternal.getStatus() != 0) {
                approvalList.add(activityInternal);
            }
        }
        return approvalList;
    }
}
