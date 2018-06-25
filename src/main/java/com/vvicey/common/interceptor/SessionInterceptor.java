package com.vvicey.common.interceptor;

import com.vvicey.login.entity.Loginer;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author nana
 * @Date 18-6-22 下午2:30
 * @Description
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("login") || uri.contains("sign") || uri.contains("error")) {
            return true;
        }
        Loginer loginer = (Loginer) SecurityUtils.getSubject().getSession().getAttribute("loginerInfo");
        if (loginer != null) {
            return true;
        }
        request.getRequestDispatcher("/login").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
