package com.vvicey.user.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.MD5Utils;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempEntity.ActivityInternal;
import com.vvicey.user.tempEntity.StudentLoginer;
import com.vvicey.user.tempEntity.TeacherLoginer;
import com.vvicey.workflow.entity.ActivityApprovalRequest;
import com.vvicey.workflow.service.ActivityApprovalRequestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 教师控制器
 */
@Controller
@RequiresPermissions("teacher")
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ActivityApprovalRequestService activityApprovalRequestService;

    /**
     * 跳转教师界面,获取学生数据
     *
     * @return 所有学生数据
     */
    @RequestMapping(value = "teacherStudentManage", method = RequestMethod.GET)
    public String teacherStudentManage(Model model) {
        List<StudentLoginer> studentLoginers = studentService.queryAllStudent();
        model.addAttribute("studentLoginers", studentLoginers);
        return "/teacher/teacherStudentManage";
    }

    /**
     * 跳转教师个人信息界面，获取登陆教师信息
     *
     * @return 教师个人数据
     */
    @RequestMapping(value = "teacherSelfInfo", method = RequestMethod.GET)
    public String teacherSelfInfo(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        TeacherLoginer teacherLoginer = teacherService.queryTeacherSelf(loginer.getUid());
        model.addAttribute("teacherLoginer", teacherLoginer);
        return "/teacher/teacherSelfInfo";
    }

    /**
     * 查询自己创建的考试事件(个人),包含考试外在因素
     *
     * @param request 教师学号
     * @return 申请的考试信息
     */
    @RequestMapping(value = "examinationInfo", method = RequestMethod.GET)
    public String examinationInfo(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        List<ActivityInternal> activityInternals = activityApprovalRequestService.queryListRequest(loginer.getUid());
        model.addAttribute("activityInternals", activityInternals);
        return "/teacher/teacherTestManage";
    }

    /**
     * 增添学生登陆账号及个人信息
     *
     * @param loginAndInfo 增添的学生个人信息和登陆信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public StudentLoginer addStudent(@RequestBody Map<String, Object> loginAndInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String loginerInfo = JSON.toJSONString(loginAndInfo.get("loginer"));
        Loginer loginer = JSON.parseObject(loginerInfo, Loginer.class);
        Student student = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("student")), Student.class);
        //创建账号
        loginService.createUser(loginer);
        //创建学生信息身份，学生uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getUsername());
        int uid = loginer.getUid();
        student.setUid(uid);
        studentService.createStudentInfo(student);
        return studentService.queryStudentSelf(uid);
    }

    /**
     * 删除学生登陆账号及个人信息
     *
     * @param uid 根据uid删除学生
     */
    @RequestMapping(value = "deleteStudent/{uid}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteStudent(@PathVariable int uid) {
        studentService.deleteStudent(uid);
    }

    /**
     * 更新学生信息
     *
     * @param loginAndStudentInfo 要更新的学生账号信息和个人信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudent", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public StudentLoginer updateStudent(@RequestBody Map<String, Object> loginAndStudentInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndStudentInfo.get("loginer")), Loginer.class);
        Student student = JSON.parseObject(JSON.toJSONString(loginAndStudentInfo.get("student")), Student.class);
        student.setUid(loginer.getUid());
        loginer.setPassword(studentService.queryStudentSelf(loginer.getUid()).getPassword());
        loginService.updateUserByUid(loginer);
        studentService.updateStudentInfoByUid(student);
        return studentService.queryStudentSelf(loginer.getUid());
    }

    /**
     * 更新教师自身登陆信息(个人)
     *
     * @param request 要更新的教师账号信息
     * @param local   需要更新的密码封装
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateTeacherLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateTeacherLoginer(HttpServletRequest request, @RequestBody Loginer local) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        //前端在username中封装了oldPassword
        String localOldPassword = MD5Utils.encryptPassword(local.getUsername());
        String remotePassword = loginService.queryUser(loginer.getUsername()).getPassword();
        if (!localOldPassword.equals(remotePassword)) {
            return Status.FAIL.getSign();
        }
        loginer.setPassword(local.getPassword());
        loginService.updateUserByUid(loginer);
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新教师自身个人信息(个人)
     *
     * @param request 需要更新的教师
     * @param teacher 要更新的教师个人信息
     */
    @RequestMapping(value = "updateTeacherInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void updateTeacherInfo(HttpServletRequest request, @RequestBody Teacher teacher) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        teacher.setUid(loginer.getUid());
        teacherService.updateTeacherInfoByUid(teacher);
    }

    /**
     * 查询教师自身信息(个人）
     *
     * @param request 登陆的uid
     * @return 教师信息
     */
    @RequestMapping(value = "queryTeacherSelf", method = RequestMethod.GET)
    @ResponseBody
    public TeacherLoginer queryStudentSelf(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return teacherService.queryTeacherSelf(loginer.getUid());
    }

    /**
     * 创建考试事件(workflow)
     *
     * @param request                用于获取提交老师的编号
     * @param activityAndExamination 需要创建的考试试卷详情和activity流
     */
    @RequestMapping(value = "createExamination", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ActivityInternal createExamination(HttpServletRequest request, @RequestBody Map<String, Object> activityAndExamination) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        int teacherNumber = teacherService.queryTeacherSelf(loginer.getUid()).getTeacherNumber();
        ActivityApprovalRequest activityApprovalRequest = JSON.parseObject(JSON.toJSONString(activityAndExamination
                .get("activityApprovalRequest")), ActivityApprovalRequest.class);
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(activityAndExamination
                .get("examinationInternal")), ExaminationInternal.class);
        activityApprovalRequest.setSubmitTeacherNumber(teacherNumber);
        return activityApprovalRequestService.createRequest(examinationInternal, activityApprovalRequest, loginer.getUid());
    }

    /**
     * 删除考试事件
     *
     * @param taskId 根据taskId删除考试事件
     */
    @RequestMapping(value = "deleteExamination/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteExamination(@PathVariable String taskId) {
        activityApprovalRequestService.deleteRequest(taskId);
    }


    /**
     * 更新考试事件
     *
     * @param examinationAndTaskId 要更新的考试事件信息和试卷编号eiid及taskId
     * @return 返回ActivityInternal
     */
    @RequestMapping(value = "updateExamination", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ActivityInternal updateExamination(@RequestBody Map<String, Object> examinationAndTaskId) {
        String taskId = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("taskId")), String.class);
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("examination")),
                ExaminationInternal.class);
        return activityApprovalRequestService.updateRequest(taskId, examinationInternal);
    }

}
