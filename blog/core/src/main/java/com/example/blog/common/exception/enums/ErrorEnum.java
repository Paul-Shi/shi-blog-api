package com.example.blog.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ErrorEnum {
    // 系统错误
    NO_AUTH(403, "没有权限"),
    UNKNOWN(500,"系统内部错误，请联系管理员"),
    TOKEN_GENERTOR_ERROR(502,  "token生成失败"),
    NO_UUID(503,"uuid为空"),

    //用户权限错误
    INVALID_TOKEN(1001, "token不合法"),

    //登录模块错误
    CAPTCHA_WARNING(10002, "验证码错误"),
    USERNAME_OR_PASSWORD_WARNING(10003, "用户名或者密码错误");

    private int code;
    private String msg;
}
