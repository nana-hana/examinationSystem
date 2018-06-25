package com.vvicey.login.controller;

import com.vvicey.common.utils.MD5Utils;
import com.vvicey.common.utils.Result;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-24 下午7:23
 * @Description 登陆控制器
 */
@Controller
@RequestMapping
public class LoginController {

    private static final int SESSION_LIFE = 600000;//session过期时间10分钟

    @Autowired
    private LoginService loginService;

    /**
     * 跳转登陆界面
     *
     * @return 登陆界面文件名
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 用户登陆检测
     *
     * @param request 获取前端传值
     * @return 返回账户验证失败或成功的状态信息
     */
    @RequestMapping(value = "loginCheck", method = RequestMethod.POST)
    @ResponseBody//返回json格式数据而不是跳转界面
    public Result loginCheck(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(true);
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

    /**
     * 增添新用户
     *
     * @param loginer 增添的用户信息
     * @return 返回增添失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addLoginer", method = RequestMethod.POST)
    @ResponseBody
    public Result addLoginer(@RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int result = loginService.createUser(loginer);
        if (result == 0) {
            return new Result(403, "添加失败");
        }
        return new Result(200, "添加成功");
    }

    /**
     * 删除用户
     *
     * @param name 要删除的用户名称
     * @return 返回删除失败或成功的状态信息
     */
    @RequestMapping(value = "deleteLoginer/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteLoginer(@PathVariable String name) {
        int result = loginService.deleteUser(name);
        if (result == 0) {
            return new Result(403, "删除失败");
        }
        return new Result(200, "删除成功");
    }

    /**
     * 更新用户
     *
     * @param loginer 要更新的用户(根据账号查找用户，id可以不输入)
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateLoginer", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateLoginer(@RequestBody Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int result = loginService.updateUser(loginer);
        if (result == 0) {
            return new Result(403, "更新失败");
        }
        return new Result(200, "更新成功");
    }

    /**
     * 查询用户
     *
     * @param name 要查询的用户名称
     * @return 返回查询失败或成功的状态信息，成功返回状态信息及查询的用户信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "queryLoginer/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result queryLoginer(@PathVariable String name) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = loginService.queryUser(name);
        if (loginer == null) {
            return new Result(403, "查询失败");
        }
        loginer.setUpassword("");
        loginer.setUpassword(MD5Utils.encryptPassword(loginer.getUpassword()));
        return new Result(200, "查询成功", loginer);
    }
}
