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

    int createUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Loginer queryUser(String username);

    int updateUserByUid(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
