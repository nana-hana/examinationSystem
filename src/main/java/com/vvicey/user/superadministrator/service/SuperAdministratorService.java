package com.vvicey.user.superadministrator.service;

import com.vvicey.user.superadministrator.entity.SuperAdministrator;
import com.vvicey.user.tempentity.SuperAdministratorLoginer;

/**
 * @Author nana
 * @Date 18-8-30 上午10:48
 * @Description 超级管理员可执行的操作抽象类
 */
public interface SuperAdministratorService {

    /**
     * 根据uid查询超级管理员信息
     *
     * @param uid 超级管理员的uid
     * @return 返回超级管理员对象
     */
    SuperAdministratorLoginer querySuperAdministratorSelf(int uid);

    /**
     * 根据超级管理员uid对超级管理员信息进行更新
     *
     * @param superAdministrator 包含需要修改uid的超级管理员信息
     */
    void updateSuperAdministratorInfoByUid(SuperAdministrator superAdministrator);
}
