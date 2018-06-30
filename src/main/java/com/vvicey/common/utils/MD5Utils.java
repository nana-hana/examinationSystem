package com.vvicey.common.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-24 下午8:49
 * @Description 密码加密及账户密码检测
 */
public class MD5Utils {

    /**
     * 对密码进行加密
     *
     * @param password 需要被编译的密码
     * @return 返回编译完成的密码
     * @throws NoSuchAlgorithmException     请求的加密算法无法实现
     * @throws UnsupportedEncodingException 编码不支持
     */
    public static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");//MD5密码加密（可能乱码）
        BASE64Encoder base64Encoder = new BASE64Encoder();//处理加密后可能的乱码
        return base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
    }

}
