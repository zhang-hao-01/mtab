package com.mtab.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.common.Constants;
import com.mtab.entity.Token;
import com.mtab.mapper.TokenMapper;
import com.mtab.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Token服务实现
 *
 * @author zhanghao
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    @Override
    public Token createToken(Integer userId, String ip, String userAgent) {
        Token token = Token.builder()
                .userId(userId)
                .token(IdUtil.fastSimpleUUID())
                .createTime((int) (System.currentTimeMillis() / 1000))
                .ip(ip)
                .userAgent(userAgent)
                .accessToken(IdUtil.fastSimpleUUID())
                .build();
        this.save(token);
        return token;
    }

    @Override
    public Token validateToken(Integer userId, String tokenStr) {
        Token token = this.getOne(new LambdaQueryWrapper<Token>()
                .eq(Token::getUserId, userId)
                .eq(Token::getToken, tokenStr));
        if (token == null) {
            return null;
        }

        // 检查Token是否过期（15天）
        LocalDateTime createTime = LocalDateTime.ofEpochSecond(token.getCreateTime(), 0, ZoneId.of("Asia/Shanghai").getRules().getOffset(java.time.Instant.ofEpochSecond(token.getCreateTime())));
        LocalDateTime expireTime = createTime.plusDays(Constants.TOKEN_EXPIRE_DAYS);
        if (LocalDateTime.now().isAfter(expireTime)) {
            // Token已过期，删除
            this.removeById(token.getId());
            return null;
        }

        // 检查是否需要刷新Token（10-15天内使用自动刷新）
        LocalDateTime refreshTime = createTime.plusDays(Constants.TOKEN_REFRESH_DAYS);
        if (LocalDateTime.now().isAfter(refreshTime)) {
            return refreshToken(token);
        }

        return token;
    }

    @Override
    public void deleteToken(Integer userId, String tokenStr) {
        this.remove(new LambdaQueryWrapper<Token>()
                .eq(Token::getUserId, userId)
                .eq(Token::getToken, tokenStr));
    }

    @Override
    public Token refreshToken(Token token) {
        token.setToken(IdUtil.fastSimpleUUID());
        token.setCreateTime((int) (System.currentTimeMillis() / 1000));
        this.updateById(token);
        return token;
    }
}
