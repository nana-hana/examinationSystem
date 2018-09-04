package com.vvicey.user.superAdministrator.dao;

import com.vvicey.user.superAdministrator.entity.SuperAdministrator;
import com.vvicey.user.tempEntity.SuperAdministratorLoginer;

/**
 * @Author nana
 * @Date 18-8-30 上午11:09
 * @Description 超级管理员数据库CRUD映射抽象类
 */
public interface SuperAdministratorMapper {
    int deleteByPrimaryKey(Integer said);

    int insertSelective(SuperAdministrator record);

    SuperAdministratorLoginer selectSuperAdministratorSelf(int uid);

    int updateByUidSelective(SuperAdministrator updateByUidSelective);
}