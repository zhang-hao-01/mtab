package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.UserSearchEngine;
import com.mtab.mapper.UserSearchEngineMapper;
import com.mtab.service.UserSearchEngineService;
import org.springframework.stereotype.Service;

/**
 * 用户搜索引擎服务实现
 *
 * @author zhanghao
 */
@Service
public class UserSearchEngineServiceImpl extends ServiceImpl<UserSearchEngineMapper, UserSearchEngine> implements UserSearchEngineService {

    @Override
    public UserSearchEngine getByUserId(Integer userId) {
        return this.getOne(new LambdaQueryWrapper<UserSearchEngine>()
                .eq(UserSearchEngine::getUserId, userId));
    }
}
