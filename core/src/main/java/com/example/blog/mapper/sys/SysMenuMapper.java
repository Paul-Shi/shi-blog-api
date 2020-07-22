package com.example.blog.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.sys.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询用户所有权限
     *
     * @param userId
     * @return
     */
    List<String> queryAllPerms(Integer userId);

    /**
     * 查询用户的menuId
     *
     * @param userId
     * @return
     */
    List<Integer> queryAllMenuId(Integer userId);
}
