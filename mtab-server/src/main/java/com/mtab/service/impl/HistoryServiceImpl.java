package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.History;
import com.mtab.mapper.HistoryMapper;
import com.mtab.service.HistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 历史记录服务实现
 *
 * @author zhanghao
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Override
    public void create(Integer userId, String linkJson) {
        History history = History.builder()
                .userId(userId)
                .link(linkJson)
                .createTime(LocalDateTime.now())
                .build();
        this.save(history);
    }

    @Override
    public Page<History> getByUserId(Integer userId, Integer page, Integer size) {
        Page<History> pageParam = new Page<>(page, size);
        return this.page(pageParam, new LambdaQueryWrapper<History>()
                .eq(History::getUserId, userId)
                .orderByDesc(History::getCreateTime));
    }

    @Override
    public void deleteById(Long id, Integer userId) {
        this.remove(new LambdaQueryWrapper<History>()
                .eq(History::getId, id)
                .eq(History::getUserId, userId));
    }

    @Override
    public History rollback(Long id, Integer userId) {
        return this.getOne(new LambdaQueryWrapper<History>()
                .eq(History::getId, id)
                .eq(History::getUserId, userId));
    }
}
