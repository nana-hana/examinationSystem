package com.vvicey.common.security;

import com.vvicey.user.login.entity.Loginer;
import com.vvicey.user.login.service.LoginService;
import com.vvicey.permission.entity.Permission;
import com.vvicey.permission.entity.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author nana
 * @Date 18-6-25 下午5:32
 * @Description shiro权限控制
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 权限授权
     *
     * @param principalCollection 权限身份集合
     * @return 返回服务器提供的身份信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Loginer loginer = loginService.queryUser(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : loginer.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (Permission permission : role.getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 登陆验证
     *
     * @param authenticationToken 收集用户提交的身份（如用户名）及凭据（如密码）
     * @return 返回服务器提供的身份信息
     * @throws AuthenticationException 获取用户信息异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //登陆所使用的账号密码
        UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToke.getUsername();
        Loginer loginer = loginService.queryUser(username);
        if (loginer == null) {
            return null;
        } else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(loginer.getUsername(), loginer.getPassword(), getName());
            SecurityUtils.getSubject().getSession().setAttribute("userInfo", loginer);
            return info;
        }

    }
}
