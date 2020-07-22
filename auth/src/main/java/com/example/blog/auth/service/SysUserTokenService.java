package com.example.blog.auth.service;

import com.example.blog.common.Result;
import com.example.blog.entity.sys.SysUserToken;

public interface SysUserTokenService {
    /**
     * 查询token
     *
     * @param toekn
     * @return
     */
    SysUserToken queryByToken(String toekn);

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    Result createToken(Integer userId);

    /**
     * 推出登录
     *
     * @param userId
     */
    void logout(Integer userId);

    /**
     * 续期
     *
     * @param userId
     * @param token
     */
    void refreshToken(Integer userId, String token);
}
