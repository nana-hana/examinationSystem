package com.vvicey.user.student.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.tracing.dtrace.Attributes;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.MD5Utils;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
import com.vvicey.examination.service.ExaminationInternalService;
import com.vvicey.testPaper.entity.CheckingQuestion;
import com.vvicey.testPaper.entity.MultipleChoice;
import com.vvicey.testPaper.entity.SingleChoice;
import com.vvicey.testPaper.service.CheckingQuestionService;
import com.vvicey.testPaper.service.MultipleChoiceService;
import com.vvicey.testPaper.service.SingleChoiceService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.student.service.StudentService;
import com.vvicey.user.tempEntity.StudentLoginer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ExaminationInternalService examinationInternalService;
    @Autowired
    private ExaminationExternalService examinationExternalService;
    @Autowired
    private SingleChoiceService singleChoiceService;
    @Autowired
    private MultipleChoiceService multipleChoiceService;
    @Autowired
    private CheckingQuestionService checkingQuestionService;

    /**
     * 跳转学生界面
     *
     * @return 学生界面文件名
     */
    @RequestMapping(value = "studentSelfInfo", method = RequestMethod.GET)
    public String studentSelfInfo(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        StudentLoginer studentLoginer = studentService.queryStudentSelf(loginer.getUid());
        model.addAttribute("studentLoginer", studentLoginer);
        return "/student/studentSelfInfo";
    }

    /**
     * 学生进行考试
     *
     * @return 返回考试界面
     */
    @RequestMapping(value = "studentBeginExamination", method = RequestMethod.GET)
    public String studentBeginExamination(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        StudentLoginer studentLoginer = studentService.queryStudentSelf(loginer.getUid());
        int studentClass = studentLoginer.getStudentClass();
        ExaminationInternal examinationInternal = examinationInternalService.queryExaminationInternalByStudentClass(studentClass);
        List<SingleChoice> singleChoiceList = singleChoiceService.querySingleChoiceBySubjectId
                (examinationInternal.getSubjectId(), examinationInternal.getSingleNumber());
        List<MultipleChoice> multipleChoiceList = multipleChoiceService.queryMultipleChoiceBySubjectId
                (examinationInternal.getSubjectId(), examinationInternal.getMultipleNumber());
        List<CheckingQuestion> checkingQuestionList = checkingQuestionService.queryCheckingQuestionBySubjectId
                (examinationInternal.getSubjectId(), examinationInternal.getCheckingNumber());
        request.getSession().setAttribute("singleChoiceList", singleChoiceList);
        request.getSession().setAttribute("multipleChoiceList", multipleChoiceList);
        request.getSession().setAttribute("checkingQuestionList", checkingQuestionList);
        String singleAnswers = (String) request.getSession().getAttribute("singleAnswers");
        String multipleAnswers = (String) request.getSession().getAttribute("multipleAnswers");
        String checkingAnswers = (String) request.getSession().getAttribute("checkingAnswers");
        if (singleAnswers != null) {
            model.addAttribute("singleAnswers", singleAnswers);
        }
        if (multipleAnswers != null) {
            model.addAttribute("multipleAnswers", multipleAnswers);
        }
        if (checkingAnswers != null) {
            model.addAttribute("checkingAnswers", checkingAnswers);
        }
        ExaminationExternal examinationExternal = examinationExternalService.queryExaminationExternalByEiid(examinationInternal.getEiid());
        String examinationTime = String.valueOf(examinationExternal.getExamTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        if (examinationTime.compareTo(nowTime) >= 0) {
            model.addAttribute("isTimeToExamination", 0);//1代表可以开始考试了，0代表还不能开始考试
            model.addAttribute("whenExamination", examinationTime);
        } else {
            model.addAttribute("isTimeToExamination", 1);
        }
        request.getSession().setAttribute("examinationInternal", examinationInternal);
        model.addAttribute("singleChoiceList", singleChoiceList);
        model.addAttribute("multipleChoiceList", multipleChoiceList);
        model.addAttribute("checkingQuestionList", checkingQuestionList);
        model.addAttribute("examinationTime", examinationInternal.getExaminationTime());
        model.addAttribute("examinationSubjectId", examinationInternal.getSubjectId());
        return "/student/studentBeginExamination";
    }

    /**
     * 保存学生考试答案
     *
     * @param request 获取session
     * @param sMCList 单、多、判答案
     */
    @RequestMapping(value = "storeStudentAnswer", method = RequestMethod.POST)
    @ResponseBody
    public void storeStudentAnswer(HttpServletRequest request, @RequestBody Map<String, Integer[]> sMCList) {
        String singleAnswers = JSON.toJSONString(sMCList.get("singleAnswers"));
        String multipleAnswers = JSON.toJSONString(sMCList.get("multipleAnswers"));
        String checkingAnswers = JSON.toJSONString(sMCList.get("checkingAnswers"));
        request.getSession().setAttribute("singleAnswers", singleAnswers);
        request.getSession().setAttribute("multipleAnswers", multipleAnswers);
        request.getSession().setAttribute("checkingAnswers", checkingAnswers);
    }

    /**
     * 返回考试结果
     *
     * @param request 获取session
     */
    @RequestMapping(value = "examinationResult", method = RequestMethod.GET)
    public String examinationResult(HttpServletRequest request, Model model) {
        ExaminationInternal examinationInternal = (ExaminationInternal) request.getSession().getAttribute("examinationInternal");
        int singleScore = examinationInternal.getSingleScore();
        int multipleScore = examinationInternal.getMultipleScore();
        int checkingScore = examinationInternal.getCheckingScore();
        int singleNum = examinationInternal.getSingleNumber();
        int multipleNum = examinationInternal.getMultipleNumber();
        int checkingNum = examinationInternal.getCheckingNumber();

        List<SingleChoice> singleChoiceList = (List<SingleChoice>) request.getSession().getAttribute("singleChoiceList");
        List<MultipleChoice> multipleChoiceList = (List<MultipleChoice>) request.getSession().getAttribute("multipleChoiceList");
        List<CheckingQuestion> checkingQuestionList = (List<CheckingQuestion>) request.getSession().getAttribute("checkingQuestionList");

        String singleAnswers = (String) request.getSession().getAttribute("singleAnswers");
        String multipleAnswers = (String) request.getSession().getAttribute("multipleAnswers");
        String checkingAnswers = (String) request.getSession().getAttribute("checkingAnswers");
        singleAnswers = singleAnswers.replace("[", "");
        singleAnswers = singleAnswers.replace(",", "");
        singleAnswers = singleAnswers.replace("]", "");
        checkingAnswers = checkingAnswers.replace("[", "");
        checkingAnswers = checkingAnswers.replace(",", "");
        checkingAnswers = checkingAnswers.replace("]", "");

        //单选题得分情况
        StringBuilder trueSingleAnswers = new StringBuilder();
        for (SingleChoice singleChoice : singleChoiceList) {
            trueSingleAnswers.append(singleChoice.getTrueAnswer());
        }
        int singleDifferent = 0;
        char[] trueSingleAnswerList = trueSingleAnswers.toString().toCharArray();
        char[] singAnswersList = singleAnswers.toCharArray();
        for (int i = 0; i < trueSingleAnswerList.length; i++) {
            if (trueSingleAnswerList[i] != singAnswersList[i]) {
                singleDifferent++;
            }
        }
        float singleResult = singleScore - singleScore / singleNum * singleDifferent;

        //多选题得分情况
        JSONArray multipleAnswersList = JSON.parseArray(multipleAnswers);
        String[] multipleAnswer = new String[multipleNum];
        for (int i = 0; i < multipleAnswersList.size(); i++) {
            JSONArray arr = (JSONArray) multipleAnswersList.get(i);
            for (int j = 0; j < arr.size(); j++) {
                if (j == 0) {
                    multipleAnswer[i] = arr.get(j).toString();
                } else {
                    multipleAnswer[i] += arr.get(j).toString();
                }
            }
        }
        int multipleDifferent = 0;
        for (int i = 0; i < multipleChoiceList.size(); i++) {
            if (!multipleAnswer[i].equals(multipleChoiceList.get(i).getTrueAnswer())) {
                multipleDifferent++;
            }
        }
        float multipleResult = multipleScore - multipleScore / multipleNum * multipleDifferent;

        //判断题得分情况
        StringBuilder trueCheckingAnswers = new StringBuilder();
        for (CheckingQuestion checkingQuestion : checkingQuestionList) {
            trueCheckingAnswers.append(checkingQuestion.getTrueAnswer());
        }
        int checkingDifferent = 0;
        char[] trueCheckingAnswersList = trueCheckingAnswers.toString().toCharArray();
        char[] checkingAnswersList = checkingAnswers.toCharArray();
        for (int i = 0; i < trueCheckingAnswersList.length; i++) {
            if (trueCheckingAnswersList[i] != checkingAnswersList[i]) {
                checkingDifferent++;
            }
        }
        float checkingResult = checkingScore - checkingScore / checkingNum * checkingDifferent;

        model.addAttribute("singleScore", singleScore);
        model.addAttribute("multipleScore", multipleScore);
        model.addAttribute("checkingScore", checkingScore);
        model.addAttribute("singleResult", singleResult);
        model.addAttribute("multipleResult", multipleResult);
        model.addAttribute("checkingResult", checkingResult);
        return "/student/examinationResult";
    }

    /**
     * 更新学生自身登陆信息(个人)
     *
     * @param request 要更新的学生账号信息
     * @param local   需要更新的密码封装
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateStudentLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public int updateStudentLoginer(HttpServletRequest request, @RequestBody Loginer local) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        //前端在username中封装了oldPassword
        String localOldPassword = MD5Utils.encryptPassword(local.getUsername());
        if (!localOldPassword.equals(loginService.queryUser(loginer.getUsername()).getPassword())) {
            return Status.FAIL.getSign();
        }
        loginer.setPassword(local.getPassword());
        loginService.updateUserByUid(loginer);
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新学生自身个人信息(个人）
     *
     * @param request 需要更新的学生
     * @param student 需要更新的学生个人信息
     */
    @RequestMapping(value = "updateStudentInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void updateStudentInfo(HttpServletRequest request, @RequestBody Student student) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        student.setUid(loginer.getUid());
        studentService.updateStudentInfoByUid(student);
    }

    /**
     * 查询学生自身信息(个人）
     *
     * @param request 登陆的uid
     * @return 学生信息
     */
    @RequestMapping(value = "queryStudentSelf", method = RequestMethod.GET)
    @ResponseBody
    public StudentLoginer queryStudentSelf(HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        return studentService.queryStudentSelf(loginer.getUid());
    }
}
