package com.vvicey.user.administrator.service;

import com.vvicey.user.administrator.entity.Administrator;

/**
 * @Author nana
 * @Date 18-6-30 上午10:40
 * @Description 管理员可执行的操作抽象类
 */
public interface AdministratorService {

    int createAdministratorInfo(Administrator administrator);

    int deleteAdministrator(int uid);

    Administrator queryAdministratorInfoByName(String name);

    Administrator queryAdministratorInfoByUid(int uid);

    int updateAdministratorInfoByName(Administrator administrator);

    int updateAdministratorInfoByUid(Administrator administrator);
}
