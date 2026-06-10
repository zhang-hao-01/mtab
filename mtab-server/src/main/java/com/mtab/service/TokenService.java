package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Token;

/**
 * Token服务接口
 *
 * @author zhanghao
 */
public interface TokenService extends IService<Token> {

    /**
     * 创建Token
     */
    Token createToken(Integer userId, String ip, String userAgent);

    /**
     * 验证Token
     */
    Token validateToken(Integer userId, String tokenStr);

    /**
     * 删除Token
     */
    void deleteToken(Integer userId, String tokenStr);

    /**
     * 刷新Token
     */
    Token refreshToken(Token token);
}
