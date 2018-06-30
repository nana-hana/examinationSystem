package com.vvicey.login.dao;

import com.vvicey.login.entity.Loginer;

/**
 * @Author nana
 * @Date 18-6-24 下午7:22
 * @Description 登陆者数据库CRUD映射抽象类
 */
public interface LoginMapper {

    int insertSelective(Loginer loginer);

    Loginer selectByUsername(String username);

    int updateByUidSelective(Loginer loginer);
}