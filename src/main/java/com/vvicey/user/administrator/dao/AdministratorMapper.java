package com.vvicey.user.administrator.dao;

import com.vvicey.user.administrator.entity.Administrator;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 管理员数据库CRUD映射抽象类
 */
public interface AdministratorMapper {
    int deleteByUid(int aid);

    int insertSelective(Administrator administrator);

    void insertAdministratorRole(int uid);

    Administrator selectByAdministratorName(String name);

    Administrator selectByUid(int uid);

    int updateByAdministratorNameSelective(Administrator administrator);

    int updateByUidSelective(Administrator updateByUidSelective);

}