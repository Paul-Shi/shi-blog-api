package com.example.blog.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.sys.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询非按钮的菜单
     *
     * @return
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 根据parentId查询菜单
     *
     * @param parentId
     * @return
     */
    List<SysMenu> queryListParentId(Integer parentId);
}
