package com.vvicey.user.administrator.dao;

import com.vvicey.user.administrator.entity.Administrator;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 管理员数据库CRUD映射抽象类
 */
public interface AdministratorMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Administrator record);

    int insertSelective(Administrator record);

    Administrator selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Administrator record);

    int updateByPrimaryKey(Administrator record);
}