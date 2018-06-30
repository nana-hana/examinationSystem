package com.vvicey.user.student.controller;

import com.vvicey.common.utils.Status;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 学生控制器
 */
@Controller
@RequiresPermissions("student")
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private LoginService loginService;

    /**
     * 跳转学生界面
     *
     * @return 学生界面文件名
     */
    @RequestMapping
    public String toStudentPage() {
        return "studentPage";
    }

    /**
     * 更新学生自身登陆信息(个人)
     *
     * @param request  要更新的学生账号信息
     * @param password 需要更新的密码
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudentSelfLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateStudentSelfLoginer(HttpServletRequest request, @RequestBody Loginer password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer.setPassword(password.getPassword());
        int result = loginService.updateUserByUid(loginer);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新学生自身个人信息(个人）
     *
     * @param request 需要更新的学生
     * @param student 需要更新的学生个人信息
     * @return 返回更新成功失败信息
     */
    @RequestMapping(value = "updateStudentSelfInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateStudentSelfInfo(HttpServletRequest request, @RequestBody Student student) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        student.setUid(loginer.getUid());
        int result = studentService.updateStudentInfoByUid(student);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 查询学生自身登陆信息(个人）
     *
     * @param request 学生账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentSelfLoginer", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryStudentSelfLoginer(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer = loginService.queryUser(loginer.getName());
        return loginer;
    }

    /**
     * 查询学生自身个人信息(个人）
     *
     * @param request 学生学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentSelfInfo", method = RequestMethod.GET)
    @ResponseBody
    public Student queryStudentSelfInfo(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return studentService.queryStudentInfoByUid(loginer.getUid());
    }
}
