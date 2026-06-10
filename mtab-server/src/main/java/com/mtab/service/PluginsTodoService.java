package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.PluginsTodo;

import java.util.List;

/**
 * 待办事项服务接口
 *
 * @author zhanghao
 */
public interface PluginsTodoService extends IService<PluginsTodo> {

    /**
     * 获取待办列表
     */
    List<PluginsTodo> list(Integer userId, String folder);

    /**
     * 添加待办
     */
    PluginsTodo add(PluginsTodo todo);

    /**
     * 更新待办
     */
    void update(PluginsTodo todo);

    /**
     * 删除待办
     */
    void del(Integer id, Integer userId);
}
