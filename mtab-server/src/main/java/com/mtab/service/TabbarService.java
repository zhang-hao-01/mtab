package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Tabbar;

/**
 * 标签栏服务接口
 *
 * @author zhanghao
 */
public interface TabbarService extends IService<Tabbar> {

    /**
     * 更新标签栏
     */
    void update(Integer userId, String tabsJson);

    /**
     * 获取标签栏
     */
    Tabbar get(Integer userId);
}
