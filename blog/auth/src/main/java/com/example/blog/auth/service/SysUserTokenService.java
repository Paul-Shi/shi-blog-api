package com.example.blog.auth.service;

import com.example.blog.common.Result;
import com.example.blog.entity.sys.SysUserToken;

public interface SysUserTokenService {
    /**
     * 生成Token
     *
     * @param userId
     * @return
     */
    Result createToken(Integer userId);

    /**
     * 查询token
     *
     * @param token
     * @return
     */
    SysUserToken queryByToken(String token);

    /**
     * 退出登录
     *
     * @param userId
     */
    void logout(Integer userId);

    /**
     * 续期
     *
     * @param userId
     * @param Token
     */
    void refreshToken(Integer userId, String Token);
}

