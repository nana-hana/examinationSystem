package com.vvicey.user.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
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
    private ExaminationInternalService examinationInternalService;
    @Autowired
    private ExaminationExternalService examinationExternalService;
    @Autowired
    private ActivityApprovalRequestService activityApprovalRequestService;

    /**
     * 跳转教师界面,获取学生数据
     *
     * @return 教师界面文件名
     */
    @RequestMapping
    public String toTeacherPage(Model model) {
        List<StudentLoginer> studentLoginers = studentService.queryAllStudent();
        model.addAttribute("studentLoginers", studentLoginers);
        return "/teacher/teacherStudentManage";
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
     * @param request  要更新的教师账号信息
     * @param password 需要更新的密码
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateTeacherLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateTeacherLoginer(HttpServletRequest request, @RequestBody Loginer password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer.setPassword(password.getPassword());
        if (loginService.updateUserByUid(loginer) == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新教师自身个人信息(个人)
     *
     * @param request 需要更新的教师
     * @param teacher 要更新的教师个人信息
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateTeacherInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateTeacherInfo(HttpServletRequest request, @RequestBody Teacher teacher) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        teacher.setUid(loginer.getUid());
        int result = teacherService.updateTeacherInfoByUid(teacher);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
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
     * @return 返回创建成功失败的信息
     */
    @RequestMapping(value = "createExamination", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int createExamination(HttpServletRequest request, @RequestBody Map<String, Object> activityAndExamination) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        int teacherNumber = teacherService.queryTeacherSelf(loginer.getUid()).getTeacherNumber();
        ActivityApprovalRequest activityApprovalRequest = JSON.parseObject(JSON.toJSONString(activityAndExamination
                .get("activityApprovalRequest")), ActivityApprovalRequest.class);
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(activityAndExamination
                .get("examinationInternal")), ExaminationInternal.class);
        activityApprovalRequest.setSubmitTeacherNumber(teacherNumber);
        activityApprovalRequestService.createRequest(examinationInternal, activityApprovalRequest);
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新考试事件
     *
     * @param examinationAndTaskId 要更新的考试事件信息和试卷编号eiid及taskId
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateExamination", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateExamination(@RequestBody Map<String, Object> examinationAndTaskId) {
        int eiid = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("eiid")), Integer.class);
        int taskId = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("taskId")), Integer.class);
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("examination")),
                ExaminationInternal.class);
        examinationInternal.setEiid(eiid);
        activityApprovalRequestService.updateRequest(taskId, examinationInternal);
        return Status.SUCCESS.getSign();
    }

    /**
     * 查询自己创建的考试事件(个人),包含考试外在因素
     *
     * @param request 教师学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryExaminationSelf", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> queryExaminationSelf(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return activityApprovalRequestService.queryListRequest(loginer.getUsername());
    }

}
