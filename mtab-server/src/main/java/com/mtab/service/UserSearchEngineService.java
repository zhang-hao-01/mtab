package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.UserSearchEngine;

/**
 * 用户搜索引擎服务接口
 *
 * @author zhanghao
 */
public interface UserSearchEngineService extends IService<UserSearchEngine> {

    /**
     * 获取用户搜索引擎列表
     */
    UserSearchEngine getByUserId(Integer userId);
}
