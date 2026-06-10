package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.LinkFolder;

import java.util.List;

/**
 * 书签文件夹服务接口
 *
 * @author zhanghao
 */
public interface LinkFolderService extends IService<LinkFolder> {

    /**
     * 获取所有文件夹
     */
    List<LinkFolder> listAll();
}
