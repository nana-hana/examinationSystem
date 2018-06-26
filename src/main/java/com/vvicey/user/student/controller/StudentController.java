package com.vvicey.user.student.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 学生控制器
 */
@Controller
@RequiresPermissions("student")
@RequestMapping("student")
public class StudentController {

    /**
     * 跳转学生界面
     *
     * @return 学生界面文件名
     */
    @RequestMapping
    public String toStudentPage() {
        return "studentPage";
    }
}
