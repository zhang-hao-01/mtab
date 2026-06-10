package com.mtab.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.common.Constants;
import com.mtab.entity.Token;
import com.mtab.entity.User;
import com.mtab.mapper.UserMapper;
import com.mtab.service.TokenService;
import com.mtab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现
 *
 * @author zhanghao
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private TokenService tokenService;

    @Override
    public Map<String, Object> login(String mail, String password, String ip, String userAgent) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getMail, mail));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查账号状态
        if (Integer.valueOf(Constants.STATUS_DISABLE).equals(user.getStatus())) {
            throw new RuntimeException("账号已被禁用");
        }

        // 检查登录失败次数
        if (user.getLoginFailCount() != null && user.getLoginFailCount() >= Constants.MAX_LOGIN_FAIL_COUNT) {
            throw new RuntimeException("登录失败次数过多，请稍后再试");
        }

        // 验证密码
        if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
            // 增加失败次数
            user.setLoginFailCount(user.getLoginFailCount() == null ? 1 : user.getLoginFailCount() + 1);
            this.updateById(user);
            throw new RuntimeException("密码错误");
        }

        // 登录成功，重置失败次数
        user.setLoginFailCount(0);
        user.setLoginIp(ip);
        user.setLoginTime(LocalDateTime.now());
        this.updateById(user);

        // 创建Token
        Token token = tokenService.createToken(user.getId(), ip, userAgent);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("token", token.getToken());
        result.put("nickname", user.getNickname());
        result.put("avatar", user.getAvatar());
        result.put("manager", user.getManager());
        return result;
    }

    @Override
    public void register(String mail, String password, String ip) {
        // 检查邮箱是否已注册
        long count = this.count(new LambdaQueryWrapper<User>().eq(User::getMail, mail));
        if (count > 0) {
            throw new RuntimeException("邮箱已被注册");
        }

        User user = User.builder()
                .mail(mail)
                .password(DigestUtil.md5Hex(password))
                .registerIp(ip)
                .loginIp(ip)
                .manager(Constants.ROLE_USER)
                .loginFailCount(0)
                .status(Constants.STATUS_ENABLE)
                .createTime(LocalDateTime.now())
                .loginTime(LocalDateTime.now())
                .build();
        this.save(user);
    }

    @Override
    public void forgetPass(String mail, String password) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getMail, mail));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(DigestUtil.md5Hex(password));
        this.updateById(user);
    }

    @Override
    public void newMail(Integer userId, String mail, String password) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        // 检查新邮箱是否已被使用
        long count = this.count(new LambdaQueryWrapper<User>()
                .eq(User::getMail, mail)
                .ne(User::getId, userId));
        if (count > 0) {
            throw new RuntimeException("邮箱已被使用");
        }
        user.setMail(mail);
        this.updateById(user);
    }

    @Override
    public void loginOut(Integer userId, String tokenStr) {
        tokenService.deleteToken(userId, tokenStr);
    }

    @Override
    public User getUserInfo(Integer userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateInfo(Integer userId, String nickname, String avatar) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        this.updateById(user);
    }

    @Override
    public void unbindQQ(Integer userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setQqOpenId(null);
        this.updateById(user);
    }
}
