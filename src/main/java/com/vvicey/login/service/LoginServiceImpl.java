package com.vvicey.login.service;

import com.vvicey.common.utils.MD5Utils;
import com.vvicey.login.dao.LoginMapper;
import com.vvicey.login.entity.Loginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-24 下午7:21
 * @Description 登陆账号的增删改查实现类
 */
@Service("LoginServiceImpl")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public int createUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        loginer.setUpassword(MD5Utils.encryptPassword(loginer.getUpassword()));//将密码进行编译在存入数据库
        return loginMapper.insertSelective(loginer);
    }

    @Override
    public int deleteUser(String name) {
        return loginMapper.deleteByName(name);
    }

    @Override
    public Loginer queryUser(String name) {
        return loginMapper.selectByName(name);
    }

    @Override
    public int updateUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        loginer.setUpassword(MD5Utils.encryptPassword(loginer.getUpassword()));
        return loginMapper.updateByNameSelective(loginer);
    }
}
