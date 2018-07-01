package com.vvicey.common.interceptor;

import com.vvicey.user.login.entity.Loginer;
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
 * @Description session拦截器
 */
public class SessionInterceptor implements HandlerInterceptor {

    /**
     * 校验是否拥有session
     *
     * @param request  http请求
     * @param response http回复
     * @param o        handler对象
     * @return 返回是否有session的信息
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     */
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
