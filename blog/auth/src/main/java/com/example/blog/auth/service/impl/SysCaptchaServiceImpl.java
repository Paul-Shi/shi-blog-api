package com.example.blog.auth.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.blog.auth.service.SysCaptchaService;
import com.example.blog.common.constants.RediskeyConstants;
import com.example.blog.common.exception.MyException;
import com.example.blog.common.exception.enums.ErrorEnum;
import com.example.blog.common.util.RedisUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 验证码过期时长5min
     */
    public final static long CAPTCHA_EXPIRE = 60 * 5;

    /**
     * 获取验证码
     *
     * @param uuid
     * @return
     */
    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new MyException(ErrorEnum.NO_UUID);
        }
        String code = producer.createText();
        //写入redis，5分钟过期
        redisUtils.set(genRedisKey(uuid), code, CAPTCHA_EXPIRE);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(code)) {
            return false;
        }
        //从redis读取
        String redisKey = genRedisKey(uuid);
        String captchaCode = redisUtils.get(redisKey);
        //删除验证码
        redisUtils.delete(redisKey);
        if (code.equalsIgnoreCase(captchaCode)) {
            return true;
        }
        return false;
    }

    private String genRedisKey(String uuid) {
        return RediskeyConstants.MANAGE_SYS_CAPTCHA + uuid;
    }
}
