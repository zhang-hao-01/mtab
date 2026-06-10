package com.mtab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.Tabbar;
import com.mtab.mapper.TabbarMapper;
import com.mtab.service.TabbarService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 标签栏服务实现
 *
 * @author zhanghao
 */
@Service
public class TabbarServiceImpl extends ServiceImpl<TabbarMapper, Tabbar> implements TabbarService {

    @Override
    public void update(Integer userId, String tabsJson) {
        Tabbar tabbar = this.getById(userId);
        if (tabbar == null) {
            tabbar = Tabbar.builder()
                    .userId(userId)
                    .tabs(tabsJson)
                    .updateTime(LocalDateTime.now())
                    .build();
            this.save(tabbar);
        } else {
            tabbar.setTabs(tabsJson);
            tabbar.setUpdateTime(LocalDateTime.now());
            this.updateById(tabbar);
        }
    }

    @Override
    public Tabbar get(Integer userId) {
        return this.getById(userId);
    }
}
