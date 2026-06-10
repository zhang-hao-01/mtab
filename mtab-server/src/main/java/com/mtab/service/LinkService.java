package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Link;

/**
 * 书签链接服务接口
 *
 * @author zhanghao
 */
public interface LinkService extends IService<Link> {

    /**
     * 更新书签
     */
    void update(Integer userId, String linkJson);

    /**
     * 获取书签
     */
    Link get(Integer userId);

    /**
     * 保存历史记录
     */
    void history(Integer userId, String linkJson);

    /**
     * 删除备份
     */
    void delBack(Integer userId, Long historyId);

    /**
     * 回滚书签
     */
    void rollBack(Integer userId, Long historyId);

    /**
     * 重置书签
     */
    void reset(Integer userId);
}
