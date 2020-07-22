package com.example.blog.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    //系统错误
    UNKNOWN(500,"系统内部错误，请联系管理员");

    private int code;
    private String msg;
}
