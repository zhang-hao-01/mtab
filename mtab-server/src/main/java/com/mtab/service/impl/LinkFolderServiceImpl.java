package com.mtab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.LinkFolder;
import com.mtab.mapper.LinkFolderMapper;
import com.mtab.service.LinkFolderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 书签文件夹服务实现
 *
 * @author zhanghao
 */
@Service
public class LinkFolderServiceImpl extends ServiceImpl<LinkFolderMapper, LinkFolder> implements LinkFolderService {

    @Override
    public List<LinkFolder> listAll() {
        return this.list();
    }
}
