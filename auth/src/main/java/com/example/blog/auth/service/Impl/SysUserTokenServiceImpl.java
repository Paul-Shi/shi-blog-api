package com.example.blog.auth.service.Impl;

import com.example.blog.auth.service.SysUserTokenService;
import com.example.blog.common.Result;
import com.example.blog.common.util.RedisUtils;
import com.example.blog.entity.sys.SysUserToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {
    /**
     * 十二小时后过期
     */
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public SysUserToken queryByToken(String token) {
        String userId = redisUtils.get(token);
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        sysUserToken.setUserId(Integer.parseInt(userId));
        return sysUserToken;
    }

    @Override
    public Result createToken(Integer userId) {
        String token = TokenGenerator
    }

    @Override
    public void logout(Integer userId) {

    }

    @Override
    public void refreshToken(Integer userId, String token) {

    }
}
