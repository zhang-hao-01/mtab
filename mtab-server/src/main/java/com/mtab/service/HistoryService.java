package com.mtab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.History;

/**
 * 历史记录服务接口
 *
 * @author zhanghao
 */
public interface HistoryService extends IService<History> {

    /**
     * 创建历史记录
     */
    void create(Integer userId, String linkJson);

    /**
     * 获取用户历史记录
     */
    Page<History> getByUserId(Integer userId, Integer page, Integer size);

    /**
     * 删除历史记录
     */
    void deleteById(Long id, Integer userId);

    /**
     * 回滚历史记录
     */
    History rollback(Long id, Integer userId);
}
