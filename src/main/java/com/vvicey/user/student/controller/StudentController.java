package com.vvicey.user.student.controller;

import com.vvicey.common.utils.Result;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
     * 更新学生自身登陆信息(未完成 个人)
     *
     * @param loginer 要更新的学生账号信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudentSelfLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateStudentSelfLoginer(@RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int result = loginService.updateUser(loginer);
        if (result == 0) {
            return new Result(403, "登陆信息更新失败");
        }
        return new Result(200, "登陆信息更新成功");
    }

    /**
     * 更新学生自身个人信息(未完成 个人）
     *
     * @param student 需要更新的学生个人信息
     * @return 返回更新成功失败信息
     */
    @RequestMapping(value = "updateStudentSelfInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateStudentSelfInfo(@RequestBody Student student) {
        int result = studentService.updateStudentInfo(student);
        if (result == 0) {
            return new Result(403, "个人信息更新失败");
        }
        return new Result(200, "个人信息更新成功");
    }

    /**
     * 查询学生自身登陆信息(未完成 个人）
     *
     * @param name 学生账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentSelfLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryStudentSelfLoginer(@PathVariable String name) {
        Loginer loginer = loginService.queryUser(name);
        if (loginer == null) {
            return new Result(403, "登录信息查询失败");
        }
        return new Result(200, "登录信息查询成功", loginer);
    }

    /**
     * 查询学生自身个人信息(未完成 个人）
     *
     * @param studentNumber 学生学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentSelfInfo/{studentNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryStudentSelfInfo(@PathVariable int studentNumber) {
        Student student = studentService.queryStudentInfo(studentNumber);
        if (student == null) {
            return new Result(403, "个人信息查询失败");
        }
        return new Result(200, "个人信息查询成功", student);
    }
}
