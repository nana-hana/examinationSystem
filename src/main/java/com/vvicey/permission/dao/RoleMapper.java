package com.vvicey.permission.dao;

import com.vvicey.permission.entity.Role;

/**
 * @Author nana
 * @Date 18-6-26 下午2:07
 * @Description 角色数据库CRUD映射抽象类
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}