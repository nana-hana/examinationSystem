package com.vvicey.user.superadministrator.controller;

import com.alibaba.fastjson.JSON;
import com.vvicey.common.information.Status;
import com.vvicey.common.utils.Md5Utils;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.administrator.service.AdministratorService;
import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.user.superadministrator.entity.SuperAdministrator;
import com.vvicey.user.superadministrator.service.SuperAdministratorService;
import com.vvicey.user.tempentity.AdministratorLoginer;
import com.vvicey.user.tempentity.SuperAdministratorLoginer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @Author nana
 * @Date 18-8-30 上午10:39
 * @Description 超级管理员控制器
 */
@Controller
@RequiresPermissions("superadministrator")
@RequestMapping("superAdministrator")
public class SuperAdministratorController {

    private final LoginService loginService;
    private final AdministratorService administratorService;
    private final SuperAdministratorService superAdministratorService;

    @Autowired
    public SuperAdministratorController(LoginService loginService, AdministratorService administratorService, SuperAdministratorService superAdministratorService) {
        this.loginService = loginService;
        this.administratorService = administratorService;
        this.superAdministratorService = superAdministratorService;
    }

    /**
     * 跳转超级管理员界面,获取管理员信息
     *
     * @return 超级管理员管理员界面文件名
     */
    @RequestMapping(value = "administratorManage", method = RequestMethod.GET)
    public String administratorManage(Model model) {
        List<AdministratorLoginer> administratorLoginers = administratorService.queryAllAdministrator();
        model.addAttribute("administratorLoginers", administratorLoginers);
        return "/superadministrator/administratorManage";
    }

    /**
     * 跳转管理员个人信息界面，获取登陆管理员信息
     *
     * @return 管理员个人数据
     */
    @RequestMapping(value = "superAdministratorSelfInfo", method = RequestMethod.GET)
    public String superAdministratorSelfInfo(Model model, HttpServletRequest request) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        SuperAdministratorLoginer superAdministratorLoginer = superAdministratorService.querySuperAdministratorSelf(loginer.getUid());
        model.addAttribute("superAdministratorLoginer", superAdministratorLoginer);
        return "/superadministrator/superAdministratorSelfInfo";
    }

    /**
     * 增添管理员登陆账号及个人信息
     *
     * @param loginAndInfo 增添管理员个人信息和登陆信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "addAdministrator", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AdministratorLoginer addAdministrator(@RequestBody Map<String, Object> loginAndInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String loginerInfo = JSON.toJSONString(loginAndInfo.get("loginer"));
        Loginer loginer = JSON.parseObject(loginerInfo, Loginer.class);
        Administrator administrator = JSON.parseObject(JSON.toJSONString(loginAndInfo.get("administrator")), Administrator.class);
        //创建账号
        loginService.createUser(loginer);
        //创建管理员信息身份，管理员个人信息uid与账号uid相关联
        loginer = loginService.queryUser(loginer.getUsername());
        int uid = loginer.getUid();
        administrator.setUid(uid);
        administratorService.createAdministratorInfo(administrator);
        return administratorService.queryAdministratorSelf(uid);
    }

    /**
     * 删除管理员登陆账号及个人信息
     *
     * @param uid 根据uid删除管理员
     */
    @RequestMapping(value = "deleteAdministrator/{uid}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdministrator(@PathVariable int uid) {
        administratorService.deleteAdministrator(uid);
    }

    /**
     * 更新管理员信息
     *
     * @param loginAndAdministratorInfo 要更新的管理员账号信息和个人信息
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateAdministrator", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AdministratorLoginer updateAdministrator(@RequestBody Map<String, Object> loginAndAdministratorInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = JSON.parseObject(JSON.toJSONString(loginAndAdministratorInfo.get("loginer")), Loginer.class);
        Administrator administrator = JSON.parseObject(JSON.toJSONString(loginAndAdministratorInfo.get("administrator")), Administrator.class);
        administrator.setUid(loginer.getUid());
        loginer.setPassword(administratorService.queryAdministratorSelf(loginer.getUid()).getPassword());
        loginService.updateUserByUid(loginer);
        administratorService.updateAdministratorInfoByUid(administrator);
        return administratorService.queryAdministratorSelf(loginer.getUid());
    }

    /**
     * 更新超级管理员自身登陆信息(个人)
     *
     * @param request 要更新的超级管理员账号信息
     * @param local   需要更新的密码封装
     * @return 返回更新失败或成功的状态信息
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @RequestMapping(value = "updateSuperAdministratorLoginer", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public int updateSuperAdministratorLoginer(HttpServletRequest request, @RequestBody Loginer local) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        if (!Md5Utils.encryptPassword(local.getUsername()).equals(loginService.queryUser(loginer.getUsername()).getPassword())) {
            return Status.FAIL.getSign();
        }
        loginer.setPassword(local.getPassword());
        loginService.updateUserByUid(loginer);
        return Status.SUCCESS.getSign();
    }

    /**
     * 更新超级管理员自身个人信息(个人)
     *
     * @param request            需要更新的超级管理员
     * @param superAdministrator 要更新的超级管理员个人信息
     */
    @RequestMapping(value = "updateSuperAdministratorInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void updateSuperAdministratorInfo(HttpServletRequest request, @RequestBody SuperAdministrator superAdministrator) {
        Loginer loginer = (Loginer) request.getSession().getAttribute("loginerInfo");
        superAdministrator.setUid(loginer.getUid());
        superAdministratorService.updateSuperAdministratorInfoByUid(superAdministrator);
    }

}
