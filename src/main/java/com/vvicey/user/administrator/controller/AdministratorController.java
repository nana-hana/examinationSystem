package com.vvicey.user.administrator.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.administrator.service.AdministratorService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempEntity.TeacherLoginer;
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
    @Autowired
    private ActivityApprovalRequestService activityApprovalRequestService;
    @Autowired
    private ExaminationExternalService examinationExternalService;

    /**
     * 跳转管理员界面,获取教师数据
     *
     * @return 管理员界面文件名
     */
    @RequestMapping
    public String toAdministratorPage(Model model) {
        List<TeacherLoginer> teacherLoginers = teacherService.queryAllTeacher();
        model.addAttribute("teacherLoginers", teacherLoginers);
        return "/administrator/administratorTeacherManage";
    }

    /**
     * 增添教师登陆账号及个人信息
     *
     * @param loginAndInfo 增添的教师个人信息和登陆信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addTeacher", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public TeacherLoginer addTeacher(@RequestBody Map<String, Object> loginAndInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String loginerInfo = JSON.toJSONString(loginAndInfo.get("loginer"));
        Loginer loginer = JSON.parseObject(loginerInfo, Loginer.class);
        Teacher teacher = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("teacher")), Teacher.class);
        //创建账号
        loginService.createUser(loginer);
        //创建教师信息身份，教师uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getUsername());
        int uid = loginer.getUid();
        teacher.setUid(uid);
        teacherService.createTeacherInfo(teacher);
        return teacherService.queryTeacherSelf(uid);
    }

    /**
     * 删除教师登陆账号及个人信息
     *
     * @param uid 根据uid删除教师
     */
    @RequestMapping(value = "deleteTeacher/{uid}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteTeacher(@PathVariable int uid) {
        teacherService.deleteTeacher(uid);
    }

    /**
     * 更新教师信息
     *
     * @param loginAndTeacherInfo 要更新的教师账号信息和个人信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateTeacher", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public TeacherLoginer updateTeacher(@RequestBody Map<String, Object> loginAndTeacherInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndTeacherInfo.get("loginer")), Loginer.class);
        Teacher teacher = JSON.parseObject(JSON.toJSONString(loginAndTeacherInfo.get("teacher")), Teacher.class);
        teacher.setUid(loginer.getUid());
        loginer.setPassword(teacherService.queryTeacherSelf(loginer.getUid()).getPassword());
        loginService.updateUserByUid(loginer);
        teacherService.updateTeacherInfoByUid(teacher);
        return teacherService.queryTeacherSelf(loginer.getUid());
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
     * 查询管理员登陆信息(个人)
     *
     * @param request 管理员账号
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的管理员信息
     */
    @RequestMapping(value = "queryAdministratorSelfLoginer", method = RequestMethod.GET)
    @ResponseBody
    public Loginer queryAdministratorSelfLoginer(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        loginer = loginService.queryUser(loginer.getUsername());
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

    /**
     * 更新ExaminationInternal审核状态信息，创建ExaminationExternal信息(创建)。
     * 前端传值的时候判断操作人员做出的操作(1通过，2不通过)，如果是1就要附带传ExaminationExternal的参数，
     * 否则只需要传eiid和approvalStatus。
     *
     * @param statusAndExaminationExternal 审批状态和需要创建的信息
     * @return 返回增添失败或成功的状态信息
     */
    @RequestMapping(value = "approvalExamination", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int approvalExamination(@RequestBody Map<String, Object> statusAndExaminationExternal) {
        activityApprovalRequestService.approve(statusAndExaminationExternal);
        return Status.SUCCESS.getSign();
    }

    /**
     * 查询管理员个人信息(个人)
     *
     * @param request 管理员uid
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的管理员信息
     */
    @RequestMapping(value = "queryNeedApprovalExamination", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> queryNeedApprovalExamination(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return activityApprovalRequestService.approvalQueryList(loginer.getUsername());
    }

    /**
     * 更新ExaminationExternal信息
     *
     * @param examinationExternalAndEiid 修改考试外在信息及该考试对应的eiid
     * @return 返回修改失败或成功的状态信息
     */
    @RequestMapping(value = "updateExaminationExternal", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int updateExaminationExternal(@RequestBody Map<String, Object> examinationExternalAndEiid) {
        int eiid = JSON.parseObject(JSON.toJSONString(examinationExternalAndEiid.get("eiid")), Integer.class);
        ExaminationExternal examinationExternal = JSON.parseObject(JSON.toJSONString(examinationExternalAndEiid
                .get("examinationExternal")), ExaminationExternal.class);
        examinationExternal.setEiid(eiid);
        examinationExternalService.updateExaminationExternalByEeid(examinationExternal);
        return Status.SUCCESS.getSign();
    }

}
