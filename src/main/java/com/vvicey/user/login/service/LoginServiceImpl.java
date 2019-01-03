package com.vvicey.user.login.service;

import com.vvicey.common.utils.Md5Utils;
import com.vvicey.user.login.dao.LoginMapper;
import com.vvicey.user.login.entity.Loginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-24 下午7:21
 * @Description 登陆账号的增删改查实现类
 */
@Service("LoginServiceImpl")
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;

    @Autowired
    public LoginServiceImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //将密码进行编译在存入数据库
        loginer.setPassword(Md5Utils.encryptPassword(loginer.getPassword()));
        return loginMapper.insertSelective(loginer);
    }

    @Override
    public Loginer queryUser(String username) {
        return loginMapper.selectByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserByUid(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        loginer.setPassword(Md5Utils.encryptPassword(loginer.getPassword()));
        return loginMapper.updateByUidSelective(loginer);
    }
}
