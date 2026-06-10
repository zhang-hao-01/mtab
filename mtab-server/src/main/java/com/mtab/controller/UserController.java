package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.User;
import com.mtab.service.UserService;
import com.mtab.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String mail = params.get("mail");
        String password = params.get("password");
        if (mail == null || password == null) {
            return Result.error("参数错误");
        }
        try {
            Map<String, Object> data = userService.login(mail, password, IpUtil.getIpAddr(request), request.getHeader("User-Agent"));
            return Result.success(data);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String mail = params.get("mail");
        String password = params.get("password");
        if (mail == null || password == null) {
            return Result.error("参数错误");
        }
        try {
            userService.register(mail, password, IpUtil.getIpAddr(request));
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/forgetPass")
    public Result<Void> forgetPass(@RequestBody Map<String, String> params) {
        String mail = params.get("mail");
        String password = params.get("password");
        if (mail == null || password == null) {
            return Result.error("参数错误");
        }
        try {
            userService.forgetPass(mail, password);
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改邮箱
     */
    @PostMapping("/newMail")
    public Result<Void> newMail(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String mail = params.get("mail");
        String password = params.get("password");
        try {
            userService.newMail(userId, mail, password);
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/loginOut")
    public Result<Void> loginOut(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String token = request.getHeader(Constants.HEADER_TOKEN);
        userService.loginOut(userId, token);
        return Result.success(null);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        try {
            return Result.success(userService.getUserInfo(userId));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateInfo")
    public Result<Void> updateInfo(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String nickname = params.get("nickname");
        String avatar = params.get("avatar");
        try {
            userService.updateInfo(userId, nickname, avatar);
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 解绑QQ
     */
    @PostMapping("/unbindQQ")
    public Result<Void> unbindQQ(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        try {
            userService.unbindQQ(userId);
            return Result.success(null);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
