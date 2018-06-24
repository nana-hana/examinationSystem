package com.vvicey.login.controller;

import com.vvicey.common.utils.Result;
import com.vvicey.common.utils.SecurityUtils;
import com.vvicey.login.entity.Loginer;
import com.vvicey.login.service.LoginService;
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
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 跳转登陆界面
     *
     * @return 返回跳转的登录界面文件名称
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登陆成功跳转页面
     *
     * @return 返回跳转的主界面文件名称
     */
    @RequestMapping(value = "logined", method = RequestMethod.GET)
    public String logined() {
        return "mainPage";
    }

    /**
     * 用户登陆检测
     *
     * @param request 获取前端传值
     * @return 返回账户验证失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "loginCheck", method = RequestMethod.POST)
    @ResponseBody//返回json格式数据而不是跳转界面
    public String loginCheck(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Loginer loginerRemote = loginService.queryUser(username);
        if (loginerRemote != null) {
            if (SecurityUtils.checkPassword(password, loginerRemote.getUpassword())) {
                //设置session 占位保证一个账号一次只能一个人登陆 先保留
                loginerRemote.setUpassword("");
                request.getSession().setAttribute("userInfo", loginerRemote);
                return "login_success";
            } else {
                return "login_fail";
            }
        } else {
            return "login_fail";
        }
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
        loginer.setUpassword(SecurityUtils.encryptPassword(loginer.getUpassword()));
        return new Result(200, "查询成功", loginer);
    }
}
