package com.vvicey.user.teacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 教师控制器
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {

    /**
     * 跳转教师界面
     *
     * @return 教师界面文件名
     */
    @RequestMapping
    public String toTeacherPage() {
        return "teacherPage";
    }
}
