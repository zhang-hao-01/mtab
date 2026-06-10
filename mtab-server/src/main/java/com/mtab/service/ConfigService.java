package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Config;

/**
 * 用户配置服务接口
 *
 * @author zhanghao
 */
public interface ConfigService extends IService<Config> {

    /**
     * 更新配置
     */
    void update(Integer userId, String configJson);

    /**
     * 获取配置
     */
    Config get(Integer userId);
}
