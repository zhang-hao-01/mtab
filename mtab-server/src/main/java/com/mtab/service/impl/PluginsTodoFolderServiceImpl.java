package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.PluginsTodoFolder;
import com.mtab.mapper.PluginsTodoFolderMapper;
import com.mtab.service.PluginsTodoFolderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 待办文件夹服务实现
 *
 * @author zhanghao
 */
@Service
public class PluginsTodoFolderServiceImpl extends ServiceImpl<PluginsTodoFolderMapper, PluginsTodoFolder> implements PluginsTodoFolderService {

    @Override
    public List<PluginsTodoFolder> list(Integer userId) {
        return this.list(new LambdaQueryWrapper<PluginsTodoFolder>()
                .eq(PluginsTodoFolder::getUserId, userId)
                .orderByDesc(PluginsTodoFolder::getCreateTime));
    }

    @Override
    public PluginsTodoFolder create(Integer userId, String name) {
        PluginsTodoFolder folder = PluginsTodoFolder.builder()
                .userId(userId)
                .name(name)
                .createTime(LocalDateTime.now())
                .build();
        this.save(folder);
        return folder;
    }

    @Override
    public void del(Integer id, Integer userId) {
        this.remove(new LambdaQueryWrapper<PluginsTodoFolder>()
                .eq(PluginsTodoFolder::getId, id)
                .eq(PluginsTodoFolder::getUserId, userId));
    }
}
