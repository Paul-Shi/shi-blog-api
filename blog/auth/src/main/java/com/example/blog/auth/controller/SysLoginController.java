package com.example.blog.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blog.auth.service.SysCaptchaService;
import com.example.blog.auth.service.SysUserTokenService;
import com.example.blog.common.Result;
import com.example.blog.common.base.AbstractController;
import com.example.blog.common.exception.enums.ErrorEnum;
import com.example.blog.entity.sys.SysUser;
import com.example.blog.entity.sys.form.SysLoginForm;
import com.example.blog.mapper.sys.SysUserMapper;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class SysLoginController extends AbstractController {
    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @PostMapping("/admin/sys/login")
    public Result login(@RequestBody SysLoginForm form) {
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            //验证码不正确
            return Result.error(ErrorEnum.CAPTCHA_WARNING);
        }

        //用户信息
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda()
                .eq(SysUser::getUsername, form.getUsername()));
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return Result.error(ErrorEnum.USERNAME_OR_PASSWORD_WARNING);
        }
        if (user.getStatus() == 0) {
            return Result.error("账户已被锁定，请联系管理员");
        }

        //生成token，并保存到redis
        return sysUserTokenService.createToken(user.getUserId());
    }

    /**
     * 推出登录
     *
     * @return
     */
    @PostMapping("/sys/logout")
    public Result logout() {
        sysUserTokenService.logout(getUserId());
        return Result.ok();
    }
}
