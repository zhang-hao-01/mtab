package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Setting;

import java.util.Map;

/**
 * 系统设置服务接口
 *
 * @author zhanghao
 */
public interface SettingService extends IService<Setting> {

    /**
     * 保存设置
     */
    void saveSetting(Map<String, String> settings);

    /**
     * 刷新缓存
     */
    void refreshCache();

    /**
     * 获取设置
     */
    Map<String, String> getSetting();
}
