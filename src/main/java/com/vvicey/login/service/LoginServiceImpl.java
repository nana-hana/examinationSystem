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

    /**
     * 创建登陆者账号
     *
     * @param loginer 需要被创建的登陆者信息
     * @return 返回创建成功与否
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @Override
    public int createUser(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        loginer.setPassword(MD5Utils.encryptPassword(loginer.getPassword()));//将密码进行编译在存入数据库
        return loginMapper.insertSelective(loginer);
    }

    /**
     * 查询登陆者信息
     *
     * @param username 根据账号来查询登陆者信息
     * @return 返回查询的结果
     */
    @Override
    public Loginer queryUser(String username) {
        return loginMapper.selectByUsername(username);
    }

    /**
     * 更新登陆者信息(根据id)
     *
     * @param loginer 需要被更新的登陆者信息
     * @return 返回更新成功与否
     * @throws UnsupportedEncodingException 编码不支持
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     */
    @Override
    public int updateUserByUid(Loginer loginer) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        loginer.setPassword(MD5Utils.encryptPassword(loginer.getPassword()));
        return loginMapper.updateByUidSelective(loginer);
    }
}
