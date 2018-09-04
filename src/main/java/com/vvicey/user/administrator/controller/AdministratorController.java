package com.vvicey.user.administrator.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.MD5Utils;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.itemBank.entity.CheckingQuestion;
import com.vvicey.itemBank.entity.MultipleChoice;
import com.vvicey.itemBank.entity.SingleChoice;
import com.vvicey.itemBank.service.CheckingQuestionService;
import com.vvicey.itemBank.service.MultipleChoiceService;
import com.vvicey.itemBank.service.SingleChoiceService;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.administrator.service.AdministratorService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.teacher.service.TeacherService;
import com.vvicey.user.tempEntity.ActivityInternal;
import com.vvicey.user.tempEntity.AdministratorLoginer;
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
import java.util.HashMap;
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
    @Autowired
    private MultipleChoiceService multipleChoiceService;
    @Autowired
    private CheckingQuestionService checkingQuestionService;
    @Autowired
    private SingleChoiceService singleChoiceService;

    /**
     * 跳转管理员界面,获取教师数据
     *
     * @return 管理员界面文件名
     */
    @RequestMapping(value = "administratorTeacherManage", method = RequestMethod.GET)
    public String administratorTeacherManage(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        AdministratorLoginer administratorLoginer = administratorService.queryAdministratorSelf(loginer.getUid());
        List<TeacherLoginer> teacherLoginers = teacherService.queryByInstitute(administratorLoginer.getInstitute());
        model.addAttribute("teacherLoginers", teacherLoginers);
        return "/administrator/administratorTeacherManage";
    }

    /**
     * 跳转管理员个人信息界面，获取登陆管理员信息
     *
     * @return 管理员个人数据
     */
    @RequestMapping(value = "administratorSelfInfo", method = RequestMethod.GET)
    public String administratorSelfInfo(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        AdministratorLoginer administratorLoginer = administratorService.queryAdministratorSelf(loginer.getUid());
        model.addAttribute("administratorLoginer", administratorLoginer);
        return "/administrator/administratorSelfInfo";
    }

    /**
     * 跳转管理员试题库管理界面，获取试题库信息
     *
     * @return 试题库信息
     */
    @RequestMapping(value = "itemBankManage", method = RequestMethod.GET)
    public String itemBankManage(Model model) {
        List<SingleChoice> allSingleChoice = singleChoiceService.querySingleChoiceAll();
        List<MultipleChoice> allMultipleChoice = multipleChoiceService.queryMultipleChoiceAll();
        List<CheckingQuestion> allCheckingQuestion = checkingQuestionService.queryCheckingQuestionAll();
        model.addAttribute("allSingleChoice", allSingleChoice);
        model.addAttribute("allMultipleChoice", allMultipleChoice);
        model.addAttribute("allCheckingQuestion", allCheckingQuestion);
        return "/administrator/itemBankManage";
    }

    /**
     * 跳转管理员考试管理界面，获取待审批信息
     *
     * @return 待审批信息
     */
    @RequestMapping(value = "administratorTestManage", method = RequestMethod.GET)
    public String administratorTestManage(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        AdministratorLoginer administratorLoginer = administratorService.queryAdministratorSelf(loginer.getUid());
        List<TeacherLoginer> teacherLoginers = teacherService.queryByInstitute(administratorLoginer.getInstitute());
        int index = 0;
        List<ActivityInternal> approvalList = activityApprovalRequestService.approvalQueryList();
        for (int i = approvalList.size() - 1; i >= 0; i--) {
            ActivityInternal activityInternal = approvalList.get(i);
            int teacherNumber = activityInternal.getSubmitTeacherNumber();
            for (TeacherLoginer teacherLoginer : teacherLoginers) {
                if (teacherNumber == teacherLoginer.getTeacherNumber()) {
                    index = 1;
                }
            }
            if (index != 1) {
                approvalList.remove(activityInternal);
            }
            index = 0;
        }


        model.addAttribute("approvalList", approvalList);
        return "administrator/administratorTestManage";
    }

    /**
     * 更新ExaminationInternal审核状态信息，创建ExaminationExternal信息(创建)。
     * 前端传值的时候判断操作人员做出的操作(1通过，2不通过)，如果是1就要附带传ExaminationExternal的参数，
     * 否则只需要传eiid和approvalStatus。
     *
     * @param statusAndExaminationExternal 审批状态和需要创建的信息
     */
    @RequestMapping(value = "approvalRequest", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void approvalRequest(@RequestBody Map<String, Object> statusAndExaminationExternal) {
        activityApprovalRequestService.approve(statusAndExaminationExternal);
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
     * 删除所选单选题试题
     *
     * @param id 根据id删除试题
     */
    @RequestMapping(value = "deleteSingleChoice/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteSingleChoice(@PathVariable int id) {
        singleChoiceService.deleteSingleChoiceById(id);
    }

    /**
     * 删除所选单选题试题
     *
     * @param id 根据id删除试题
     */
    @RequestMapping(value = "deleteMultipleChoice/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteMultipleChoice(@PathVariable int id) {
        multipleChoiceService.deleteMultipleChoiceById(id);
    }

    /**
     * 删除所选单选题试题
     *
     * @param id 根据id删除试题
     */
    @RequestMapping(value = "deleteCheckingQuestion/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public void deleteCheckingQuestion(@PathVariable int id) {
        checkingQuestionService.deleteCheckingQuestionById(id);
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
     * @param request 要更新的管理员账号信息
     * @param local   需要更新的密码封装
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateAdministratorLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateAdministratorLoginer(HttpServletRequest request, @RequestBody Loginer local) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        String remotePassword = loginService.queryUser(loginer.getUsername()).getPassword();
        if (!MD5Utils.encryptPassword(local.getUsername()).equals(remotePassword)) {
            return Status.FAIL.getSign();
        }
        loginer.setPassword(local.getPassword());
        loginService.updateUserByUid(loginer);
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新管理员自身个人信息(个人)
     *
     * @param request       需要更新的管理员
     * @param administrator 要更新的管理员个人信息
     */
    @RequestMapping(value = "updateAdministratorInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void updateAdministratorInfo(HttpServletRequest request, @RequestBody Administrator administrator) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        administrator.setUid(loginer.getUid());
        administratorService.updateAdministratorInfoByUid(administrator);
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

    /**
     * 根据前台eiid查询当前考试题目
     *
     * @param request 获取信息
     * @return 返回查询的试题
     */
    @RequestMapping("/queryTest.do")
    @ResponseBody
    public Map<String, Object> queryTest(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
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
            modelMap.put("multuple", "empty");
        } else {
            modelMap.put("multuple", multipleChoiceList);
        }
        if (checkingQuestionList == null || checkingQuestionList.isEmpty()) {
            modelMap.put("check", "empty");
        } else {
            modelMap.put("check", checkingQuestionList);
        }
        return modelMap;
    }
}
