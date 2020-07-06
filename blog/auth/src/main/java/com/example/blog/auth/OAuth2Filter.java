package com.example.blog.auth;

import com.example.blog.common.Result;
import com.example.blog.common.exception.enums.ErrorEnum;
import com.example.blog.common.util.HttpContextUtils;
import com.example.blog.common.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2Filter extends AuthenticatingFilter {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Filter.class);

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
            String json = JsonUtils.toJson(Result.error(ErrorEnum.INVALID_TOKEN));
            httpServletResponse.getWriter().print(json);

            return false;
        }
        return executeLogin(servletRequest, servletResponse);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        if (((HttpServletRequest) servletRequest).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result r = Result.error(ErrorEnum.NO_AUTH.getCode(), throwable.getMessage());
            String json = JsonUtils.toJson(r);
            httpServletResponse.getWriter().print(json);
        } catch (Exception exception) {
            logger.error("处理登录失败的异常", exception);
        }
        return false;
    }

    /**
     * 获取请求的token
     *
     * @param httpServletRequest
     * @return
     */
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
