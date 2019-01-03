package com.vvicey.user.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.Md5Utils;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.itembank.entity.CheckingQuestion;
import com.vvicey.itembank.entity.MultipleChoice;
import com.vvicey.itembank.entity.SingleChoice;
import com.vvicey.itembank.service.CheckingQuestionService;
import com.vvicey.itembank.service.MultipleChoiceService;
import com.vvicey.itembank.service.SingleChoiceService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempentity.ActivityInternal;
import com.vvicey.user.tempentity.StudentLoginer;
import com.vvicey.user.tempentity.TeacherLoginer;
import com.vvicey.workflow.entity.ActivityApprovalRequest;
import com.vvicey.workflow.service.ActivityApprovalRequestService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

    private final StudentService studentService;
    private final LoginService loginService;
    private final TeacherService teacherService;
    private final ActivityApprovalRequestService activityApprovalRequestService;
    private final ExaminationInternalService examinationInternalService;
    private final MultipleChoiceService multipleChoiceService;
    private final CheckingQuestionService checkingQuestionService;
    private final SingleChoiceService singleChoiceService;

    @Autowired
    public TeacherController(StudentService studentService, LoginService loginService, TeacherService teacherService, ActivityApprovalRequestService activityApprovalRequestService, ExaminationInternalService examinationInternalService, MultipleChoiceService multipleChoiceService, CheckingQuestionService checkingQuestionService, SingleChoiceService singleChoiceService) {
        this.studentService = studentService;
        this.loginService = loginService;
        this.teacherService = teacherService;
        this.activityApprovalRequestService = activityApprovalRequestService;
        this.examinationInternalService = examinationInternalService;
        this.multipleChoiceService = multipleChoiceService;
        this.checkingQuestionService = checkingQuestionService;
        this.singleChoiceService = singleChoiceService;
    }

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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public int updateTeacherLoginer(HttpServletRequest request, @RequestBody Loginer local) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        //前端在username中封装了oldPassword
        String localOldPassword = Md5Utils.encryptPassword(local.getUsername());
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public ActivityInternal updateExamination(@RequestBody Map<String, Object> examinationAndTaskId) {
        String taskId = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("taskId")), String.class);
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(examinationAndTaskId.get("examination")),
                ExaminationInternal.class);
        return activityApprovalRequestService.updateRequest(taskId, examinationInternal);
    }

    /**
     * 题目导入控制器
     *
     * @param file    上传的文本文件
     * @param request 获取信息
     * @return modelMap转为json格式返回，status为1，无错误返回，status为0，有错误信息，错误信息存在error_info
     */
    @SuppressWarnings("finally")
    @RequestMapping("/inputExamination.do")
    @ResponseBody
    public Map<String, String> inputExamination(@RequestParam("doc") MultipartFile file, HttpServletRequest request) {
        Map<String, String> modelMap = new HashMap<>(2048);
        //将前台json字符串转为ExaminationInternal对象
        String eiidStr = request.getParameter("eiid");
        int eiid = Integer.parseInt(eiidStr);
        List<SingleChoice> singleTestList = singleChoiceService.querySingleChoiceByEiid(eiid);
        List<MultipleChoice> multipleChoiceList = multipleChoiceService.queryMultipleChoiceByEiid(eiid);
        List<CheckingQuestion> checkList = checkingQuestionService.queryCheckingQuestionByEiid(eiid);
        if (!singleTestList.isEmpty() || !multipleChoiceList.isEmpty() || !checkList.isEmpty()) {
            modelMap.put("status", "0");
            modelMap.put("error_info", "导入失败，该考试题目已经导入过");
            return modelMap;
        }
        //通过前台传来的eiid获得当前考试信息
        ExaminationInternal exam = examinationInternalService.queryExaminationInternalByEiid(eiid);
        // 原始名称
        String originalFileName = file.getOriginalFilename();
        //将文件存到e盘下，临时文件，存哪里都可以
        File newFile = new File("/home/nana/" + originalFileName);
        try {
            // 将内存中的数据写入磁盘
            file.transferTo(newFile);
            //题目写入数据库
            List<String> list = teacherService.examInput(newFile.toString(), exam);
            if (list == null || list.isEmpty()) {
                modelMap.put("status", "1");
            } else {
                modelMap.put("status", "0");
                //存放错误信息
                StringBuilder errorInfo = new StringBuilder();
                for (String string : list) {
                    errorInfo.append(string);
                    errorInfo.append("\n");
                }
                modelMap.put("error_info", errorInfo.toString());
            }
        } catch (Exception e) {
            modelMap.put("status", "0");
            modelMap.put("error_info", "题目写入失败");
            e.printStackTrace();
        } finally {
            if (newFile.exists()) {
                newFile.delete();
            }
        }
        return modelMap;
    }

    /**
     * 根据前台eiid查询当前考试题目
     *
     * @param request request
     * @return 返回当前考试题目
     */
    @RequestMapping("/queryTest.do")
    @ResponseBody
    public Map<String, Object> queryTest(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(2048);
        //获取eiid转为int类型
        String eiidStr = request.getParameter("eiid");
        int examEiid = Integer.parseInt(eiidStr);
        //查询单选题
        List<SingleChoice> singleChoiceList = singleChoiceService.querySingleChoiceByEiid(examEiid);
        //查询多选题
        List<MultipleChoice> multipleChoiceList = multipleChoiceService.queryMultipleChoiceByEiid(examEiid);
        //查询判断题
        List<CheckingQuestion> checkingQuestionList = checkingQuestionService.queryCheckingQuestionByEiid(examEiid);
        //将题目信息添加进modelMap
        if (singleChoiceList == null || singleChoiceList.isEmpty()) {
            modelMap.put("single", "empty");
        } else {
            modelMap.put("single", singleChoiceList);
        }
        if (multipleChoiceList == null || multipleChoiceList.isEmpty()) {
            modelMap.put("multiple", "empty");
        } else {
            modelMap.put("multiple", multipleChoiceList);
        }
        if (checkingQuestionList == null || checkingQuestionList.isEmpty()) {
            modelMap.put("check", "empty");
        } else {
            modelMap.put("check", checkingQuestionList);
        }
        return modelMap;
    }
}
