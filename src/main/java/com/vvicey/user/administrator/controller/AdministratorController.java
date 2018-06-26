package com.vvicey.user.administrator.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author nana
 * @Date 18-6-25 下午2:16
 * @Description 管理员控制器
 */
@Controller
@RequiresPermissions("administrator")
@RequestMapping("administrator")
public class AdministratorController {

    /**
     * 跳转管理员界面
     *
     * @return 管理员界面文件名
     */
    @RequestMapping
    public String toAdministratorPage() {
        return "administratorPage";
    }
}
