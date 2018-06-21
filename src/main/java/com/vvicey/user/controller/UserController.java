package com.vvicey.user.controller;

import com.vvicey.user.entity.User;
import com.vvicey.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("userController")
@RequestMapping("user")
@SessionAttributes(value = "sessionUser")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login")
    public String loginUser(ModelMap modelMap, User user) {
        User theUser = userService.checkUser(user.getUsername(), user.getPassword());
        if (theUser == null) {
            return "../../index";
        }
        modelMap.addAttribute("sessionUser", theUser.getUsername());
        return "redirect:/new/showNews";
    }

    @RequestMapping(value = "register")
    public String registerUser(ModelMap modelMap, User user) {
        int isSuccess = userService.createUser(user);
        modelMap.addAttribute("isSuccess",isSuccess);
        return "../../index";
    }

}
