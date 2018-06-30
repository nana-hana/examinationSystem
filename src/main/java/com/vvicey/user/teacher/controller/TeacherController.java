package com.vvicey.user.teacher.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.utils.Status;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 跳转教师界面
     *
     * @return 教师界面文件名
     */
    @RequestMapping
    public String toTeacherPage() {
        return "teacherPage";
    }

    /**
     * 增添学生登陆账号及个人信息
     *
     * @param loginAndInfo 增添的学生个人信息和登陆信息
     * @return 返回增添失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int addStudent(@RequestBody Map<String, Object> loginAndInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("loginer")), Loginer.class);
        Student student = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("student")), Student.class);
        //检查账号重复
        Loginer checkResult = loginService.queryUser(loginer.getName());
        if (checkResult != null) {
            return Status.FAIL_REPETITION.getSign();
        }
        //创建账号
        int loginerResult = loginService.createUser(loginer);
        if (loginerResult == 0) {
            return Status.FAIL.getSign();
        }
        //创建学生信息身份，学生uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getName());
        student.setUid(loginer.getUid());
        int studentResult = studentService.createStudentInfo(student);
        if (studentResult == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 删除学生登陆账号及个人信息
     *
     * @param student 获取学生学号
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "deleteStudent", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public int deleteStudent(@RequestBody Student student) {
        student = studentService.queryStudentInfoByStudentNumber(student.getStudentNumber());
        if (student == null) {
            return Status.NOT_EXIST.getSign();
        }
        int result = studentService.deleteStudent(student.getUid());
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新学生登陆信息
     *
     * @param loginAndStudentNumber 要更新的学生账号信息和学号
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudentLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateStudentLoginer(@RequestBody Map<String, Object> loginAndStudentNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndStudentNumber.get("loginer")), Loginer.class);
        int studentNumber = JSON.parseObject(JSON.toJSONString(loginAndStudentNumber.get("studentNumber")), Integer.class);
        Student student = studentService.queryStudentInfoByStudentNumber(studentNumber);
        int uid = student.getUid();
        loginer.setUid(uid);
        int result = loginService.updateUserByUid(loginer);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新学生个人信息
     *
     * @param studentAndStudentNumber 要更新的学生个人信息和学号
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateStudentInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateStudentInfo(@RequestBody Map<String, Object> studentAndStudentNumber) {
        Student student = JSON.parseObject(JSON.toJSONString(studentAndStudentNumber.get("student")), Student.class);
        int studentNumber = JSON.parseObject(JSON.toJSONString(studentAndStudentNumber.get("studentNumber")), Integer.class);
        int uid = studentService.queryStudentInfoByStudentNumber(studentNumber).getUid();
        student.setUid(uid);
        int result = studentService.updateStudentInfoByUid(student);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
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
     * 查询学生登陆信息
     *
     * @param name 学生账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryStudentLoginer(@PathVariable String name) {
        return loginService.queryUser(name);
    }

    /**
     * 查询学生个人信息
     *
     * @param studentNumber 学生学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentInfo/{studentNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Student queryStudentInfo(@PathVariable int studentNumber) {
        return studentService.queryStudentInfoByStudentNumber(studentNumber);
    }

    /**
     * 查询教师登陆信息(个人)
     *
     * @param request 教师账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryTeacherSelfLoginer", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryTeacherSelfLoginer(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer = loginService.queryUser(loginer.getName());
        return loginer;
    }

    /**
     * 查询教师个人信息(个人)
     *
     * @param request 教师学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryTeacherSelfInfo", method = RequestMethod.GET)
    @ResponseBody
    public Teacher queryTeacherSelfInfo(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return teacherService.queryTeacherInfoByUid(loginer.getUid());
    }

    /**
     * 创建考试事件
     *
     * @param request             用于获取提交老师的编号
     * @param examinationInternal 需要创建的考试试卷详情
     * @return 返回创建成功失败的信息
     */
    @RequestMapping(value = "createExamination", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int createExamination(HttpServletRequest request, @RequestBody ExaminationInternal examinationInternal) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        int teacherNumber = teacherService.queryTeacherInfoByUid(loginer.getUid()).getTeacherNumber();
        examinationInternal.setSubmitTeacherNumber(teacherNumber);
        int examinationResult = examinationInternalService.createExamination(examinationInternal);
        if (examinationResult == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 删除考试事件
     *
     * @param examinationInternal 用于获取考试事件号eiid
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "deleteExamination", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public int deleteExamination(@RequestBody ExaminationInternal examinationInternal) {
        int eiid = examinationInternal.getEiid();
        int result = examinationInternalService.deleteExamination(eiid);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 查询自己创建的考试事件(个人)
     *
     * @param request 教师学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryExaminationSelf", method = RequestMethod.GET)
    @ResponseBody
    public List<ExaminationInternal> queryExaminationSelf(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        int teacherNumber = teacherService.queryTeacherInfoByUid(loginer.getUid()).getTeacherNumber();
        return examinationInternalService.queryExaminationByTeacherNumber(teacherNumber);
    }

    /**
     * 更新考试事件
     *
     * @param examinationAndEiid 要更新的考试事件信息和试卷编号eiid
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "updateExamination", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateExamination(@RequestBody Map<String, Object> examinationAndEiid, HttpServletRequest request) {
        ExaminationInternal examinationInternal = JSON.parseObject(JSON.toJSONString(examinationAndEiid.get("examination")), ExaminationInternal.class);
        int eiid = JSON.parseObject(JSON.toJSONString(examinationAndEiid.get("eiid")), Integer.class);
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        int teacherNumber = teacherService.queryTeacherInfoByUid(loginer.getUid()).getTeacherNumber();
        examinationInternal.setEiid(eiid);
        examinationInternal.setSubmitTeacherNumber(teacherNumber);
        int result = examinationInternalService.updateExaminationByEiid(examinationInternal);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

}
