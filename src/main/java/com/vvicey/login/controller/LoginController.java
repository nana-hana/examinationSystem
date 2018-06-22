package com.vvicey.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

//    @Autowired
//    private UserService userService;

    @RequestMapping
    public String login() {

        return "login";
    }

//    @RequestMapping("/check")
//    @ResponseBody//异步请求，不输入的话会寻找return的界面
//    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");

//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(true);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            SecurityUtils.getSubject().getSession().setTimeout(1800000);
//        } catch (Exception e) {
//            return "login_fail";
//        }
//        return "login_success";

        //查询数据库，如果查到数据，将密码进行MD5加密然后再与数据库的密码进行比较
//        User user = userService.findUserByUserName(username);
//        if (user != null) {
//            if (SecurityUtils.checkPassword(password, user.getPassword())) {
//                //校验成功，session设置
//                request.getSession().setAttribute("userInfo", user);
//                return "login_success";
//            } else {
//                //失败，返回提示信息
//                return "login_fail";
//            }
//        } else {
//            return "login_fail";
//        }
//    }

//    @RequestMapping("/register")
//    @ResponseBody
//    public String register(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {//Json
//        userService.createUser(user);
//        return "success";
//    }
}
