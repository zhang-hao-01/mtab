package com.mtab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.LinkFolder;
import com.mtab.entity.LinkStore;

import java.util.List;

/**
 * 书签商店服务接口
 *
 * @author zhanghao
 */
public interface LinkStoreService extends IService<LinkStore> {

    /**
     * 用户列表
     */
    Page<LinkStore> list(Integer page, Integer size, String keyword, String type, Integer groupId);

    /**
     * 管理员列表
     */
    Page<LinkStore> listManager(Integer page, Integer size, String keyword, Integer status);

    /**
     * 获取文件夹
     */
    List<LinkFolder> getFolder();

    /**
     * 管理员获取文件夹
     */
    List<LinkFolder> getFolderAdmin();

    /**
     * 添加书签
     */
    void add(LinkStore linkStore, Integer userId);

    /**
     * 添加公开书签
     */
    void addPublic(LinkStore linkStore, Integer userId);

    /**
     * 推送书签
     */
    void push(Integer id, Integer userId);

    /**
     * 获取图标
     */
    String getIcon(String url);

    /**
     * 更新安装数量
     */
    void installNum(Integer id);

    /**
     * 创建文件夹
     */
    void createFolder(String name, String groupIds);

    /**
     * 移动分组
     */
    void moveGroup(Integer id, String groupIds);

    /**
     * 移动文件夹
     */
    void moveFolder(Integer id, Integer folderId);

    /**
     * 排序文件夹
     */
    void sortFolder(List<Integer> ids);

    /**
     * 删除书签
     */
    void del(Integer id);

    /**
     * 获取域名列表
     */
    List<String> domains();
}
