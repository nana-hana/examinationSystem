package com.vvicey.user.administrator.service;

import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempEntity.AdministratorLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 上午10:40
 * @Description 管理员可执行的操作抽象类
 */
public interface AdministratorService {

    int createAdministratorInfo(Administrator administrator);

    int deleteAdministrator(int uid);

    List<AdministratorLoginer> queryAllAdministrator();

    AdministratorLoginer queryAdministratorSelf(int uid);

    int updateAdministratorInfoByName(Administrator administrator);

    void updateAdministratorInfoByUid(Administrator administrator);
}
