package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Note;

import java.util.List;

/**
 * 便签服务接口
 *
 * @author zhanghao
 */
public interface NoteService extends IService<Note> {

    /**
     * 获取便签列表
     */
    List<Note> get(Long userId);

    /**
     * 排序便签
     */
    void sort(List<Long> ids);

    /**
     * 获取便签内容
     */
    Note getText(Long id, Long userId);

    /**
     * 设置权重
     */
    void setWeight(Long id, Integer weight, Long userId);

    /**
     * 删除便签
     */
    void del(Long id, Long userId);

    /**
     * 添加便签
     */
    Note add(Long userId, String title, String text);

    /**
     * 更新便签
     */
    void update(Long id, Long userId, String title, String text);
}
