package com.example.blog.common.exception;

import com.example.blog.common.exception.enums.ErrorEnum;

public class MyException extends RuntimeException{
    private String msg;
    private int code = 500;

    public MyException() {
        super(ErrorEnum.UNKNOWN.getMsg());
        this.msg = ErrorEnum.UNKNOWN.getMsg();
    }

    public MyException(ErrorEnum errorEnum, Throwable e) {
        super(errorEnum.getMsg(), e);
        this.msg = errorEnum.getMsg();
        this.code = errorEnum.getCode();
    }

    public MyException(ErrorEnum errorEnum) {
        this.msg = errorEnum.getMsg();
        this.code = errorEnum.getCode();
    }

    public MyException(String exception) {
        this.msg = exception;
    }
}
