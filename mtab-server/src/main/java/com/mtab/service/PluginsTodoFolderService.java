package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.PluginsTodoFolder;

import java.util.List;

/**
 * 待办文件夹服务接口
 *
 * @author zhanghao
 */
public interface PluginsTodoFolderService extends IService<PluginsTodoFolder> {

    /**
     * 获取文件夹列表
     */
    List<PluginsTodoFolder> list(Integer userId);

    /**
     * 创建文件夹
     */
    PluginsTodoFolder create(Integer userId, String name);

    /**
     * 删除文件夹
     */
    void del(Integer id, Integer userId);
}
