package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.SearchEngine;

import java.util.List;

/**
 * 搜索引擎服务接口
 *
 * @author zhanghao
 */
public interface SearchEngineService extends IService<SearchEngine> {

    /**
     * 首页搜索引擎列表
     */
    List<SearchEngine> index();

    /**
     * 管理列表
     */
    List<SearchEngine> list();

    /**
     * 添加搜索引擎
     */
    void add(SearchEngine searchEngine);

    /**
     * 删除搜索引擎
     */
    void del(Integer id);

    /**
     * 获取搜索引擎
     */
    SearchEngine searchEngine(Integer id);

    /**
     * 保存用户自定义搜索引擎
     */
    void saveSearchEngine(Integer userId, String listJson);

    /**
     * 排序搜索引擎
     */
    void sort(List<Integer> ids);
}
