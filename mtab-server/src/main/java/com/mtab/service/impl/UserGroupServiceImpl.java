package com.mtab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.UserGroup;
import com.mtab.mapper.UserGroupMapper;
import com.mtab.service.UserGroupService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户组服务实现
 *
 * @author zhanghao
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Override
    public List<UserGroup> list() {
        return this.list();
    }

    @Override
    public void create(String name) {
        UserGroup group = UserGroup.builder()
                .name(name)
                .createTime(LocalDateTime.now())
                .sort(0)
                .build();
        this.save(group);
    }

    @Override
    public void edit(Long id, String name) {
        UserGroup group = this.getById(id);
        if (group == null) {
            throw new RuntimeException("用户组不存在");
        }
        group.setName(name);
        this.updateById(group);
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    public void sort(List<Long> ids) {
        for (int i = 0; i < ids.size(); i++) {
            UserGroup group = this.getById(ids.get(i));
            if (group != null) {
                group.setSort(i);
                this.updateById(group);
            }
        }
    }
}
