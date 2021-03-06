package com.vvicey.user.login.controller;

import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
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

    /**
     * session过期时间10分钟
     */
    private static final int SESSION_LIFE = 600000;

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

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
    @ResponseBody
    public Loginer loginCheck(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取用户主体(current user)
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(SESSION_LIFE);
        } catch (Exception e) {
            return null;
        }
        Loginer loginer = loginService.queryUser(username);
        loginer.setPassword("");
        request.getSession().setAttribute("loginerInfo", loginer);
        return loginer;
    }

    /**
     * 账号登出
     *
     * @return 返回登陆界面
     */
    @RequestMapping(value = "loginOut", method = RequestMethod.GET)
    public String loginOut() {
        SecurityUtils.getSubject().getSession().stop();
        return "login";
    }
}
