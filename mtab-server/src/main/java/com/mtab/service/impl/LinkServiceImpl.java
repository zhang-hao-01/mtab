package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.History;
import com.mtab.entity.Link;
import com.mtab.mapper.LinkMapper;
import com.mtab.service.HistoryService;
import com.mtab.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 书签链接服务实现
 *
 * @author zhanghao
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private HistoryService historyService;

    @Override
    public void update(Integer userId, String linkJson) {
        Link link = this.getById(userId);
        if (link == null) {
            link = Link.builder()
                    .userId(userId)
                    .link(linkJson)
                    .updateTime(LocalDateTime.now())
                    .build();
            this.save(link);
        } else {
            link.setLink(linkJson);
            link.setUpdateTime(LocalDateTime.now());
            this.updateById(link);
        }
        // 保存历史记录
        historyService.create(userId, linkJson);
    }

    @Override
    public Link get(Integer userId) {
        return this.getById(userId);
    }

    @Override
    public void history(Integer userId, String linkJson) {
        historyService.create(userId, linkJson);
    }

    @Override
    public void delBack(Integer userId, Long historyId) {
        historyService.deleteById(historyId, userId);
    }

    @Override
    public void rollBack(Integer userId, Long historyId) {
        History history = historyService.rollback(historyId, userId);
        if (history != null) {
            Link link = this.getById(userId);
            if (link == null) {
                link = Link.builder()
                        .userId(userId)
                        .link(history.getLink())
                        .updateTime(LocalDateTime.now())
                        .build();
                this.save(link);
            } else {
                link.setLink(history.getLink());
                link.setUpdateTime(LocalDateTime.now());
                this.updateById(link);
            }
        }
    }

    @Override
    public void reset(Integer userId) {
        this.removeById(userId);
    }
}
