package com.vvicey.permission.dao;

import com.vvicey.permission.entity.Permission;

/**
 * @Author nana
 * @Date 18-6-26 下午2:07
 * @Description 权限数据库CRUD映射抽象类
 */
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}