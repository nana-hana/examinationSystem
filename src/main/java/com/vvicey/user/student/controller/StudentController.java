package com.vvicey.user.student.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.ExamFileInputUtil;
import com.vvicey.common.utils.Md5Utils;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.examination.service.ExaminationExternalService;
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
import com.vvicey.user.tempentity.StudentLoginer;
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

    private final StudentService studentService;
    private final LoginService loginService;
    private final ExaminationInternalService examinationInternalService;
    private final ExaminationExternalService examinationExternalService;
    private final SingleChoiceService singleChoiceService;
    private final MultipleChoiceService multipleChoiceService;
    private final CheckingQuestionService checkingQuestionService;

    @Autowired
    public StudentController(StudentService studentService, LoginService loginService, ExaminationInternalService examinationInternalService, ExaminationExternalService examinationExternalService, SingleChoiceService singleChoiceService, MultipleChoiceService multipleChoiceService, CheckingQuestionService checkingQuestionService) {
        this.studentService = studentService;
        this.loginService = loginService;
        this.examinationInternalService = examinationInternalService;
        this.examinationExternalService = examinationExternalService;
        this.singleChoiceService = singleChoiceService;
        this.multipleChoiceService = multipleChoiceService;
        this.checkingQuestionService = checkingQuestionService;
    }

    private static final int COMPUTER = 0;
    private static final int MATH = 1;
    private static final int ENGLISH = 2;

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
        List<ExaminationInternal> examinationInternalList = examinationInternalService.queryExaminationInternalByStudentClass(studentClass);
        ExaminationInternal examinationInternal = null;
        long minTime = 99999;
        for (ExaminationInternal examInternal : examinationInternalList) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String examinationTime = df.format(examInternal.getExamTime());
            String nowTime = df.format(new Date());
            Date examinationTimeDate;
            Date nowTimeDate;
            long diffMinutes;
            try {
                examinationTimeDate = df.parse(examinationTime);
                nowTimeDate = df.parse(nowTime);
                long diff = examinationTimeDate.getTime() - nowTimeDate.getTime();
                diffMinutes = diff / (60 * 1000);
                if (diffMinutes > -examInternal.getExaminationTime()) {
                    if (diffMinutes < minTime) {
                        minTime = diffMinutes;
                        examinationInternal = examInternal;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (examinationInternal == null) {
            model.addAttribute("haveExam", 0);
            return "/student/studentBeginExamination";
        }
        int paperKind = examinationInternal.getPaperKind();
        List checkingQuestionList;
        List multipleChoiceList;
        List singleChoiceList;
        if (paperKind == 0) {
            int singleNumber = examinationInternal.getSingleNumber();
            int multipleNumber = examinationInternal.getMultipleNumber();
            int checkingNumber = examinationInternal.getCheckingNumber();
            List<SingleChoice> querySingleChoiceAll = singleChoiceService.querySingleChoiceAllBySubjectId(examinationInternal.getSubjectId());
            List<MultipleChoice> queryMultipleChoiceAll = multipleChoiceService.queryMultipleChoiceAllBySubjectId(examinationInternal.getSubjectId());
            List<CheckingQuestion> queryCheckingQuestionAll = checkingQuestionService.queryCheckingQuestionAllBySubjectId(examinationInternal.getSubjectId());
            checkingQuestionList = ExamFileInputUtil.getRandomList(queryCheckingQuestionAll, checkingNumber);
            multipleChoiceList = ExamFileInputUtil.getRandomList(queryMultipleChoiceAll, multipleNumber);
            singleChoiceList = ExamFileInputUtil.getRandomList(querySingleChoiceAll, singleNumber);
        } else {
            singleChoiceList = singleChoiceService
                    .querySingleChoiceByEiid(examinationInternal.getEiid());
            multipleChoiceList = multipleChoiceService
                    .queryMultipleChoiceByEiid(examinationInternal.getEiid());
            checkingQuestionList = checkingQuestionService
                    .queryCheckingQuestionByEiid(examinationInternal.getEiid());
        }
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
        ExaminationExternal examinationExternal = examinationExternalService
                .queryExaminationExternalByEiid(examinationInternal.getEiid());
        if (examinationExternal == null) {
            model.addAttribute("examOver", 1);
            // 1代表可以开始考试了，0代表还不能开始考试
            model.addAttribute("isTimeToExamination", 0);
            request.getSession().setAttribute("examinationInternal", examinationInternal);
            model.addAttribute("singleChoiceList", singleChoiceList);
            model.addAttribute("multipleChoiceList", multipleChoiceList);
            model.addAttribute("checkingQuestionList", checkingQuestionList);
            return "/student/studentBeginExamination";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String examinationTime = df.format(examinationExternal.getExamTime());
        String nowTime = df.format(new Date());
        Date examinationTimeDate;
        Date nowTimeDate;
        long diffMinutes = 0;
        try {
            examinationTimeDate = df.parse(examinationTime);
            nowTimeDate = df.parse(nowTime);
            long diff = examinationTimeDate.getTime() - nowTimeDate.getTime();
            diffMinutes = diff / (60 * 1000);
            System.out.print("两个时间相差：");
            System.out.print(diffMinutes + " 分钟, ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (examinationTime.compareTo(nowTime) >= 0) {
            // 1代表可以开始考试了，0代表还不能开始考试
            model.addAttribute("isTimeToExamination", 0);
            model.addAttribute("whenExamination", examinationTime);
        } else {
            model.addAttribute("isTimeToExamination", 1);
        }
        request.getSession().setAttribute("examinationInternal", examinationInternal);
        model.addAttribute("singleChoiceList", singleChoiceList);
        model.addAttribute("multipleChoiceList", multipleChoiceList);
        model.addAttribute("checkingQuestionList", checkingQuestionList);
        model.addAttribute("examinationTime", examinationInternal.getExaminationTime() + diffMinutes);
        String subjectName;
        if (examinationInternal.getSubjectId() == COMPUTER) {
            subjectName = "计算机网络";
        } else if (examinationInternal.getSubjectId() == MATH) {
            subjectName = "数学";
        } else if (examinationInternal.getSubjectId() == ENGLISH) {
            subjectName = "英语";
        } else {
            subjectName = "未知课程";
        }
        model.addAttribute("examinationSubjectId", examinationInternal.getSubjectId());
        model.addAttribute("subjectName", subjectName);
        if (diffMinutes < -examinationInternal.getExaminationTime()) {
            model.addAttribute("isTimeToExamination", 0);
            model.addAttribute("examOver", 1);
        } else {
            model.addAttribute("examOver", 0);
        }
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
        ExaminationInternal examinationInternal = (ExaminationInternal) request.getSession()
                .getAttribute("examinationInternal");
        int singleScore = examinationInternal.getSingleScore();
        int multipleScore = examinationInternal.getMultipleScore();
        int checkingScore = examinationInternal.getCheckingScore();
        int singleNum = examinationInternal.getSingleNumber();
        int multipleNum = examinationInternal.getMultipleNumber();
        int checkingNum = examinationInternal.getCheckingNumber();
        List<SingleChoice> singleChoiceList = (List<SingleChoice>) request.getSession()
                .getAttribute("singleChoiceList");
        List<MultipleChoice> multipleChoiceList = (List<MultipleChoice>) request.getSession()
                .getAttribute("multipleChoiceList");
        List<CheckingQuestion> checkingQuestionList = (List<CheckingQuestion>) request.getSession()
                .getAttribute("checkingQuestionList");
        String singleAnswers = (String) request.getSession().getAttribute("singleAnswers");
        String multipleAnswers = (String) request.getSession().getAttribute("multipleAnswers");
        String checkingAnswers = (String) request.getSession().getAttribute("checkingAnswers");
        singleAnswers = singleAnswers.replace("[", "");
        singleAnswers = singleAnswers.replace(",", "");
        singleAnswers = singleAnswers.replace("]", "");
        checkingAnswers = checkingAnswers.replace("[", "");
        checkingAnswers = checkingAnswers.replace(",", "");
        checkingAnswers = checkingAnswers.replace("]", "");
        // 单选题得分情况
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
        float singleResult = singleScore * singleNum - singleScore * singleDifferent;
        // 多选题得分情况
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
        float multipleResult = multipleScore * multipleNum - multipleScore * multipleDifferent;
        // 判断题得分情况
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
        float checkingResult = checkingScore * checkingNum - checkingScore * checkingDifferent;
        model.addAttribute("singleScore", singleScore * singleNum);
        model.addAttribute("multipleScore", multipleScore * multipleNum);
        model.addAttribute("checkingScore", checkingScore * checkingNum);
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
    @Transactional(rollbackFor = Exception.class)
    public int updateStudentLoginer(HttpServletRequest request, @RequestBody Loginer local)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        // 前端在username中封装了oldPassword
        String localOldPassword = Md5Utils.encryptPassword(local.getUsername());
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
    @Transactional(rollbackFor = Exception.class)
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
