package com.example.blog.common.base;

import com.example.blog.entity.sys.SysUser;
import org.apache.shiro.SecurityUtils;

public class AbstractController {
    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Integer getUserId() {
        return getUser().getUserId();
    }
}
