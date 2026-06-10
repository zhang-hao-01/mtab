package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.common.Constants;
import com.mtab.entity.Setting;
import com.mtab.mapper.SettingMapper;
import com.mtab.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统设置服务实现
 *
 * @author zhanghao
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveSetting(Map<String, String> settings) {
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            Setting setting = this.getById(entry.getKey());
            if (setting == null) {
                setting = Setting.builder()
                        .keys(entry.getKey())
                        .value(entry.getValue())
                        .build();
                this.save(setting);
            } else {
                setting.setValue(entry.getValue());
                this.updateById(setting);
            }
        }
        refreshCache();
    }

    @Override
    public void refreshCache() {
        List<Setting> settings = this.list();
        for (Setting setting : settings) {
            redisTemplate.opsForValue().set(Constants.REDIS_SETTING_PREFIX + setting.getKeys(), setting.getValue());
        }
    }

    @Override
    public Map<String, String> getSetting() {
        List<Setting> settings = this.list();
        Map<String, String> result = new HashMap<>();
        for (Setting setting : settings) {
            result.put(setting.getKeys(), setting.getValue());
        }
        return result;
    }
}
