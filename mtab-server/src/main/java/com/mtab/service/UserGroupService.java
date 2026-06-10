package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.UserGroup;

import java.util.List;

/**
 * 用户组服务接口
 *
 * @author zhanghao
 */
public interface UserGroupService extends IService<UserGroup> {

    /**
     * 获取用户组列表
     */
    List<UserGroup> list();

    /**
     * 创建用户组
     */
    void create(String name);

    /**
     * 编辑用户组
     */
    void edit(Long id, String name);

    /**
     * 删除用户组
     */
    void delete(Long id);

    /**
     * 排序用户组
     */
    void sort(List<Long> ids);
}
