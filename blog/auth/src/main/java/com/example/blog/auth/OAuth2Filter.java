package com.example.blog.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) servletRequest);

        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token，如果token不存在，则直接返回401
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isEmpty(token)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        }
    }

    private String getRequestToken(HttpServletRequest httpServletRequest) {
        //从header中获取token
        String token = httpServletRequest.getHeader("token");

        //如果header中不存在token则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }

        return token;
    }
}
