package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.User;

import java.util.Map;

/**
 * 用户服务接口
 *
 * @author zhanghao
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    Map<String, Object> login(String mail, String password, String ip, String userAgent);

    /**
     * 用户注册
     */
    void register(String mail, String password, String ip);

    /**
     * 忘记密码
     */
    void forgetPass(String mail, String password);

    /**
     * 修改邮箱
     */
    void newMail(Integer userId, String mail, String password);

    /**
     * 退出登录
     */
    void loginOut(Integer userId, String token);

    /**
     * 获取用户信息
     */
    User getUserInfo(Integer userId);

    /**
     * 更新用户信息
     */
    void updateInfo(Integer userId, String nickname, String avatar);

    /**
     * 解绑QQ
     */
    void unbindQQ(Integer userId);
}
