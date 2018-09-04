package com.vvicey.user.superAdministrator.service;

import com.vvicey.user.superAdministrator.entity.SuperAdministrator;
import com.vvicey.user.tempEntity.SuperAdministratorLoginer;

/**
 * @Author nana
 * @Date 18-8-30 上午10:48
 * @Description 超级管理员可执行的操作抽象类
 */
public interface SuperAdministratorService {

    SuperAdministratorLoginer querySuperAdministratorSelf(int uid);

    void updateSuperAdministratorInfoByUid(SuperAdministrator superAdministrator);
}
