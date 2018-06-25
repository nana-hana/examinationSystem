package com.vvicey.common.security;

import com.vvicey.common.utils.MD5Utils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author nana
 * @Date 18-6-25 下午5:40
 * @Description 密码校验
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 用于对服务器及用户输入的密码进行比对
     *
     * @param token 收集用户提交的身份（如用户名）及凭据（如密码）
     * @param info  服务器中提供的身份信息
     * @return 返回校验结果
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        try {
            UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            String password = String.valueOf(userToken.getPassword());
            Object tokenCredentials = MD5Utils.encryptPassword(password);
            Object accountCredentials = getCredentials(info);//服务器查询得出的身份信息
            return equals(tokenCredentials, accountCredentials);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
