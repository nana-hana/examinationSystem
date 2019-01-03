package com.vvicey.workflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.itembank.dao.CheckingQuestionMapper;
import com.vvicey.itembank.dao.MultipleChoiceMapper;
import com.vvicey.itembank.dao.SingleChoiceMapper;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempentity.ActivityInternal;
import com.vvicey.workflow.dao.ActivityApprovalRequestMapper;
import com.vvicey.workflow.entity.ActivityApprovalRequest;

/**
 * @Author nana
 * @Date 18-7-2 下午1:56
 * @Description 考试申请审批工作流实现类
 */
@Service("ActivityApprovalRequestServiceImpl")
public class ActivityApprovalRequestServiceImpl implements ActivityApprovalRequestService {

    /**
     * bpmn表id
     */
    private static final java.lang.String APPROVAL_REQUEST_FLOW_ID = "approval_request";
    /**
     * 通过
     */
    private static final int APPROVAL_REQUEST_STATUS_PSSS = 1;
    /**
     * 拒绝
     */
    private static final int APPROVAL_REQUEST_STATUS_REFUSE = 2;

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final ActivityApprovalRequestMapper activityApprovalRequestMapper;
    private final ExaminationInternalService examinationInternalService;
    private final ExaminationExternalService examinationExternalService;
    private final CheckingQuestionMapper checkingQuestionMapper;
    private final MultipleChoiceMapper multipleChoiceMapper;
    private final SingleChoiceMapper singleChoiceMapper;

    @Autowired
    public ActivityApprovalRequestServiceImpl(RuntimeService runtimeService, TaskService taskService, TeacherService teacherService, ActivityApprovalRequestMapper activityApprovalRequestMapper, ExaminationInternalService examinationInternalService, ExaminationExternalService examinationExternalService, CheckingQuestionMapper checkingQuestionMapper, MultipleChoiceMapper multipleChoiceMapper, SingleChoiceMapper singleChoiceMapper) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.activityApprovalRequestMapper = activityApprovalRequestMapper;
        this.examinationInternalService = examinationInternalService;
        this.examinationExternalService = examinationExternalService;
        this.checkingQuestionMapper = checkingQuestionMapper;
        this.multipleChoiceMapper = multipleChoiceMapper;
        this.singleChoiceMapper = singleChoiceMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityInternal createRequest(ExaminationInternal examinationInternal, ActivityApprovalRequest activityApprovalRequest, int uid) {
        examinationInternalService.createExamination(examinationInternal);
        activityApprovalRequest.setEiid(examinationInternal.getEiid());
        activityApprovalRequestMapper.insertSelective(activityApprovalRequest);
        Map<String, Object> map = new HashMap<>(2048);
        map.put("activity", activityApprovalRequest);
        map.put("teacherUid", uid);
        //给map增加条目指定受理请求的人，受理人通过processVariableValueEquals名字对申请进行审批
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(APPROVAL_REQUEST_FLOW_ID, map);
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.complete(task.getId(), map);
        return activityApprovalRequestMapper.selectByEiidAI(examinationInternal.getEiid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public void deleteRequest(String taskId) {
        Map<String, Object> variable = taskService.getVariables(taskId);
        ActivityApprovalRequest activityApprovalRequest = (ActivityApprovalRequest) variable.get("activity");
        int eiid = activityApprovalRequest.getEiid();
        singleChoiceMapper.deleteByEiid(eiid);
        checkingQuestionMapper.deleteByEiid(eiid);
        multipleChoiceMapper.deleteByEiid(eiid);
        activityApprovalRequestMapper.deleteByEiid(eiid);
        examinationInternalService.deleteExamination(eiid);
        taskService.complete(taskId);
        taskService.deleteTask(taskId, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        } else {
            activityApprovalRequest.setStatus(APPROVAL_REQUEST_STATUS_REFUSE);
            activityApprovalRequestMapper.updateByEiidSelective(activityApprovalRequest);
        }
        taskService.complete(taskId);
    }

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
