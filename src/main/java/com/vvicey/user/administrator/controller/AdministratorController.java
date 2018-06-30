package com.vvicey.user.administrator.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.utils.Status;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.administrator.service.AdministratorService;
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
import java.util.Map;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 管理员控制器
 */
@Controller
@RequiresPermissions("administrator")
@RequestMapping("administrator")
public class AdministratorController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 跳转管理员界面
     *
     * @return 管理员界面文件名
     */
    @RequestMapping
    public String toAdministratorPage() {
        return "administratorPage";
    }

    /**
     * 增添教师登陆账号及个人信息
     *
     * @param loginAndInfo 增添的教师个人信息和登陆信息
     * @return 返回增添失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addTeacher", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int addTeacher(@RequestBody Map<String, Object> loginAndInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("loginer")), Loginer.class);
        Teacher teacher = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("teacher")), Teacher.class);
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
        //创建教师信息身份，教师uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getName());
        teacher.setUid(loginer.getUid());
        int teacherResult = teacherService.createTeacherInfo(teacher);
        if (teacherResult == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 删除教师登陆账号及个人信息
     *
     * @param teacher 获取教师学号
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "deleteTeacher", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public int deleteTeacher(@RequestBody Teacher teacher) {
        teacher = teacherService.queryTeacherInfoByTeacherNumber(teacher.getTeacherNumber());
        if (teacher == null) {
            return Status.NOT_EXIST.getSign();
        }
        int result = teacherService.deleteTeacher(teacher.getUid());
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新教师登陆信息
     *
     * @param loginAndTeacherNumber 要更新的教师账号信息和编号
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateTeacherLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateTeacherLoginer(@RequestBody Map<String, Object> loginAndTeacherNumber) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndTeacherNumber.get("loginer")), Loginer.class);
        int teacherNumber = JSON.parseObject(JSON.toJSONString(loginAndTeacherNumber.get("teacherNumber")), Integer.class);
        Teacher teacher = teacherService.queryTeacherInfoByTeacherNumber(teacherNumber);
        int uid = teacher.getUid();
        loginer.setUid(uid);
        int result = loginService.updateUserByUid(loginer);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新教师个人信息
     *
     * @param teacherAndTeacherNumber 要更新的教师个人信息和学号
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateTeacherInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateTeacherInfo(@RequestBody Map<String, Object> teacherAndTeacherNumber) {
        Teacher teacher = JSON.parseObject(JSON.toJSONString(teacherAndTeacherNumber.get("teacher")), Teacher.class);
        int teacherNumber = JSON.parseObject(JSON.toJSONString(teacherAndTeacherNumber.get("teacherNumber")), Integer.class);
        int uid = teacherService.queryTeacherInfoByTeacherNumber(teacherNumber).getUid();
        teacher.setUid(uid);
        int result = teacherService.updateTeacherInfoByUid(teacher);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新管理员自身登陆信息(个人)
     *
     * @param request  要更新的管理员账号信息
     * @param password 需要更新的密码
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateAdministratorSelfLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateAdministratorLoginer(HttpServletRequest request, @RequestBody Loginer password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer.setPassword(password.getPassword());
        if (loginService.updateUserByUid(loginer) == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新管理员自身个人信息(个人)
     *
     * @param request       需要更新的管理员
     * @param administrator 要更新的管理员个人信息
     * @return 返回更新失败或成功的状态信息
     */
    @RequestMapping(value = "updateAdministratorSelfInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateAdministratorInfo(HttpServletRequest request, @RequestBody Administrator administrator) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        administrator.setUid(loginer.getUid());
        int result = administratorService.updateAdministratorInfoByUid(administrator);
        if (result == 0) {
            return Status.FAIL.getSign();
        }
        return Status.SUCCESS.getSign();
    }

    /**
     * 查询教师登陆信息
     *
     * @param name 教师账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryTeacherLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryTeacherLoginer(@PathVariable String name) {
        return loginService.queryUser(name);
    }

    /**
     * 查询教师个人信息
     *
     * @param teacherNumber 教师编号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的教师信息
     */
    @RequestMapping(value = "queryTeacherInfo/{teacherNumber}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher queryTeacherInfo(@PathVariable int teacherNumber) {
        return teacherService.queryTeacherInfoByTeacherNumber(teacherNumber);
    }

    /**
     * 查询管理员登陆信息(个人)
     *
     * @param request 管理员账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的管理员信息
     */
    @RequestMapping(value = "queryAdministratorSelfLoginer", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryAdministratorSelfLoginer(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer = loginService.queryUser(loginer.getName());
        return loginer;
    }

    /**
     * 查询管理员个人信息(个人)
     *
     * @param request 管理员uid
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的管理员信息
     */
    @RequestMapping(value = "queryAdministratorSelfInfo", method = RequestMethod.GET)
    @ResponseBody
    public Administrator queryAdministratorSelfInfo(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return administratorService.queryAdministratorInfoByUid(loginer.getUid());
    }
}
