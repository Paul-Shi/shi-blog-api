package com.example.blog.auth.service;

import com.example.blog.entity.sys.SysUser;
import com.example.blog.entity.sys.SysUserToken;

import java.util.Set;

public interface ShiroService {
    /**
     * 获取用户全部权限
     *
     * @param userId
     * @return
     */
    Set<String> getUserPermissions(Integer userId);

    /**
     * 查询token
     *
     * @param token
     * @return
     */
    SysUserToken queryByToken(String token);

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    SysUser queryUser(Integer userId);

    /**
     * 续期
     *
     * @param userId
     * @param accessToken
     */
    void refreshToken(Integer userId, String accessToken);
}
