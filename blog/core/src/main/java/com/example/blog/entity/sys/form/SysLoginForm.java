package com.example.blog.entity.sys.form;

import lombok.Data;

@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
