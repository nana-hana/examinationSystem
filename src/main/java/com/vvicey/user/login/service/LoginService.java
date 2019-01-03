package com.vvicey.user.login.service;

import com.vvicey.user.login.entity.Loginer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-24 下午7:19
 * @Description 登陆账号的增删改查抽象类
 */
public interface LoginService {
    /**
     * 创建登陆者账号
     *
     * @param loginer 需要被创建的登陆者信息
     * @return 返回创建成功与否
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    int createUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 查询登陆者信息
     *
     * @param username 根据账号来查询登陆者信息
     * @return 返回查询的结果
     */
    Loginer queryUser(String username);

    /**
     * 更新登陆者信息(根据id)
     *
     * @param loginer 需要被更新的登陆者信息
     * @return 返回更新成功与否
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    int updateUserByUid(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
