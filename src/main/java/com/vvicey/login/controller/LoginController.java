package com.vvicey.login.controller;

import com.vvicey.common.utils.Result;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author nana
 * @Date 18-6-24 下午7:23
 * @Description 登陆控制器
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private static final int SESSION_LIFE = 600000;//session过期时间10分钟

    @Autowired
    private LoginService loginService;

    /**
     * 跳转登陆界面
     *
     * @return 登陆界面文件名
     */
    @RequestMapping
    public String login() {
        return "login";
    }

    /**
     * 用户登陆检测
     *
     * @param request 获取前端传值
     * @return 返回账户验证失败或成功的状态信息
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    @ResponseBody//返回json格式数据而不是跳转界面
    public Result loginCheck(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();//获取用户主体(current user)
        try {
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(SESSION_LIFE);
        } catch (Exception e) {
            return new Result(403, "登陆失败");
        }
        Loginer loginer = loginService.queryUser(username);
        request.getSession().setAttribute("loginerInfo", loginer);
        return new Result(200, "登陆成功", loginer);
    }
}
