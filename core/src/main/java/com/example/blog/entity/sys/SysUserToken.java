package com.example.blog.entity.sys;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysUserToken对象", description = "系统用户Token")
public class SysUserToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String token;

}
