package com.mtab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.Config;
import com.mtab.mapper.ConfigMapper;
import com.mtab.service.ConfigService;
import org.springframework.stereotype.Service;

/**
 * 用户配置服务实现
 *
 * @author zhanghao
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public void update(Integer userId, String configJson) {
        Config config = this.getById(userId);
        if (config == null) {
            config = Config.builder()
                    .userId(userId)
                    .config(configJson)
                    .build();
            this.save(config);
        } else {
            config.setConfig(configJson);
            this.updateById(config);
        }
    }

    @Override
    public Config get(Integer userId) {
        if (userId == null) {
            return null;
        }
        return this.getById(userId);
    }
}
