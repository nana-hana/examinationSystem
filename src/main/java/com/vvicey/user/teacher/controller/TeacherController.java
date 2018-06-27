package com.vvicey.user.teacher.controller;

import com.vvicey.common.utils.Result;
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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
     * @param student 增添的学生个人信息
     * @param loginer 增添的学生登陆信息
     * @return 返回增添失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Result addStudent(@RequestBody Student student, @RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //检查账号重复
        Loginer checkResult = loginService.queryUser(loginer.getUname());
        if (checkResult == null) {
            return new Result(403, "账号重复");
        }
        //创建账号
        int loginerResult = loginService.createUser(loginer);
        if (loginerResult == 0) {
            return new Result(403, "账号创建失败");
        }
        //创建学生信息，学生uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getUname());
        student.setUid(loginer.getUid());
        int studentResult = studentService.createStudentInfo(student);
        if (studentResult == 0) {
            return new Result(403, "学生个人信息插入失败");
        }
        return new Result(200, "创建成功");
    }

    /**
     * 删除学生登陆账号及个人信息
     *
     * @param studentNumber 要删除的学生学号
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "delete/{studentNumber}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public Result deleteStudent(@PathVariable int studentNumber) {
        Student student = studentService.queryStudentInfo(studentNumber);
        if (student == null) {
            return new Result(403, "没有该学号学生");
        }
        int result = studentService.deleteStudent(student.getUid());
        if (result == 0) {
            return new Result(403, "账号删除失败");
        }
        return new Result(200, "账号删除成功");
    }

    /**
     * 更新学生登陆信息
     *
     * @param loginer 要更新的学生账号信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudentLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateStudentLoginer(@RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int result = loginService.updateUser(loginer);
        if (result == 0) {
            return new Result(403, "登陆信息更新失败");
        }
        return new Result(200, "登陆信息更新成功");
    }

    /**
     * 更新学生个人信息
     *
     * @param student 要更新的学生个人信息
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateStudentInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateStudentInfo(@RequestBody Student student) {
        int result = studentService.updateStudentInfo(student);
        if (result == 0) {
            return new Result(403, "个人信息更新失败");
        }
        return new Result(200, "个人信息更新成功");
    }

    /**
     * 更新教师自身登陆信息(未完成 个人)
     *
     * @param loginer 要更新的教师账号信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateTeacherLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateTeacherLoginer(@RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int result = loginService.updateUser(loginer);
        if (result == 0) {
            return new Result(403, "登陆信息更新失败");
        }
        return new Result(200, "登陆信息更新成功");
    }

    /**
     * 更新教师自身个人信息(未完成 个人)
     *
     * @param student 要更新的教师个人信息
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateTeacherInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public Result updateTeacherInfo(@RequestBody Student student) {
        int result = studentService.updateStudentInfo(student);
        if (result == 0) {
            return new Result(403, "个人信息更新失败");
        }
        return new Result(200, "个人信息更新成功");
    }

    /**
     * 查询学生登陆信息
     *
     * @param name 学生账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryStudentLoginer(@PathVariable String name) {
        Loginer loginer = loginService.queryUser(name);
        if (loginer == null) {
            return new Result(403, "登录信息查询失败");
        }
        return new Result(200, "登录信息查询成功", loginer);
    }

    /**
     * 查询学生个人信息
     *
     * @param studentNumber 学生学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryStudentInfo/{studentNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryStudentInfo(@PathVariable int studentNumber) {
        Student student = studentService.queryStudentInfo(studentNumber);
        if (student == null) {
            return new Result(403, "个人信息查询失败");
        }
        return new Result(200, "个人信息查询成功", student);
    }

    /**
     * 查询教师登陆信息(未完成 个人)
     *
     * @param name 教师账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryTeacherLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryTeacherLoginer(@PathVariable String name) {
        Loginer loginer = loginService.queryUser(name);
        if (loginer == null) {
            return new Result(403, "登录信息查询失败");
        }
        return new Result(200, "登录信息查询成功", loginer);
    }

    /**
     * 查询教师个人信息(未完成 个人)
     *
     * @param teacherNumber 学生学号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的学生信息
     */
    @RequestMapping(value = "queryTeacherInfo/{teacherNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryTeacherInfo(@PathVariable int teacherNumber) {
        Teacher teacher = teacherService.queryTeacherInfo(teacherNumber);
        if (teacher == null) {
            return new Result(403, "个人信息查询失败");
        }
        return new Result(200, "个人信息查询成功", teacher);
    }

}
