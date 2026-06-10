package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.PluginsTodo;
import com.mtab.mapper.PluginsTodoMapper;
import com.mtab.service.PluginsTodoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 待办事项服务实现
 *
 * @author zhanghao
 */
@Service
public class PluginsTodoServiceImpl extends ServiceImpl<PluginsTodoMapper, PluginsTodo> implements PluginsTodoService {

    @Override
    public List<PluginsTodo> list(Integer userId, String folder) {
        LambdaQueryWrapper<PluginsTodo> wrapper = new LambdaQueryWrapper<PluginsTodo>()
                .eq(PluginsTodo::getUserId, userId);
        if (folder != null) {
            wrapper.eq(PluginsTodo::getFolder, folder);
        }
        wrapper.orderByDesc(PluginsTodo::getWeight);
        return this.list(wrapper);
    }

    @Override
    public PluginsTodo add(PluginsTodo todo) {
        todo.setCreateTime(LocalDateTime.now());
        todo.setStatus(0);
        this.save(todo);
        return todo;
    }

    @Override
    public void update(PluginsTodo todo) {
        PluginsTodo existing = this.getById(todo.getId());
        if (existing == null) {
            throw new RuntimeException("待办事项不存在");
        }
        if (todo.getTodo() != null) {
            existing.setTodo(todo.getTodo());
        }
        if (todo.getStatus() != null) {
            existing.setStatus(todo.getStatus());
        }
        if (todo.getExpireTime() != null) {
            existing.setExpireTime(todo.getExpireTime());
        }
        if (todo.getWeight() != null) {
            existing.setWeight(todo.getWeight());
        }
        if (todo.getFolder() != null) {
            existing.setFolder(todo.getFolder());
        }
        this.updateById(existing);
    }

    @Override
    public void del(Integer id, Integer userId) {
        this.remove(new LambdaQueryWrapper<PluginsTodo>()
                .eq(PluginsTodo::getId, id)
                .eq(PluginsTodo::getUserId, userId));
    }
}
