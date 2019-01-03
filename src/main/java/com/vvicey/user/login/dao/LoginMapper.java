package com.vvicey.user.login.dao;

import com.vvicey.user.login.entity.Loginer;
import org.springframework.stereotype.Repository;

/**
 * @Author nana
 * @Date 18-6-24 下午7:22
 * @Description 登陆者数据库CRUD映射抽象类
 */
@Repository
public interface LoginMapper {

    /**
     * 插入登陆信息
     *
     * @param loginer 登陆信息
     * @return 返回插入结果
     */
    int insertSelective(Loginer loginer);

    /**
     * 根据账号查询登录信息
     *
     * @param username 账号
     * @return 返回搜索结果
     */
    Loginer selectByUsername(String username);

    /**
     * 根据uid更新登录信息
     *
     * @param loginer 登录信息
     * @return 返回更新结果
     */
    int updateByUidSelective(Loginer loginer);
}