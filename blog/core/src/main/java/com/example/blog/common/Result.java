package com.example.blog.common;

import com.example.blog.common.exception.enums.ErrorEnum;
import org.omg.CORBA.UNKNOWN;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    public Result() {
        put("code", 200);
        put("msg", "success");
    }

    public static Result ok() {
        return new Result();
    }

    public static Result error() {
        return error(ErrorEnum.UNKNOWN);
    }

    public static Result error(ErrorEnum errorEnum) {
        return new Result().put("code", errorEnum.getCode()).put("msg", errorEnum.getMsg());
    }

    public static Result error(String msg) {
        return new Result().put("msg", msg).put("code", ErrorEnum.UNKNOWN.getCode());
    }

    public static Result error (Integer code, String msg) {
        return new Result().put("code", code).put("msg", msg);
    }

    public static Result exception() {
        return exception(ErrorEnum.UNKNOWN);
    }

    public static Result exception(ErrorEnum errorEnum) {
        return new Result().put("code", errorEnum.getCode()).put("msg", errorEnum.getMsg());
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
