package com.example.blog.auth.service.impl;

import com.example.blog.auth.service.SysUserTokenService;
import com.example.blog.auth.TokenGenerator;
import com.example.blog.common.Result;
import com.example.blog.common.constants.RediskeyConstants;
import com.example.blog.common.util.RedisUtils;
import com.example.blog.entity.sys.SysUserToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    @Override
    public Result createToken(Integer userId) {
        String token = TokenGenerator.generateValue();

        String tokenKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + token;
        String userIdKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + userId;

        //判断是否生成过token
        String tokenInRedis = redisUtils.get(userIdKey);
        if (!StringUtils.isEmpty(tokenInRedis)) {
            //将原来的token删除
            redisUtils.delete(RediskeyConstants.MANAGE_SYS_USER_TOKEN + tokenInRedis);
        }
        //将token存进redis
        redisUtils.set(tokenKey, userId, EXPIRE);
        redisUtils.set(userIdKey, token, EXPIRE);

        return new Result().put("token", token).put("expire", EXPIRE);
    }

    /**
     * 查询token
     *
     * @param token
     * @return
     */
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

    /**
     * 退出登录
     *
     * @param userId
     */
    @Override
    public void logout(Integer userId) {
        String userIdKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + userId;
        String token = redisUtils.get(userIdKey);
        String tokenKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + token;
        redisUtils.delete(userIdKey);
        redisUtils.delete(tokenKey);
    }

    /**
     * 续期
     *
     * @param userId
     * @param token
     */
    @Override
    public void refreshToken(Integer userId, String token) {
        String tokenKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + token;
        String userIdKey = RediskeyConstants.MANAGE_SYS_USER_TOKEN + userId;
        redisUtils.updateExpire(tokenKey);
        redisUtils.updateExpire(userIdKey);
    }
}
