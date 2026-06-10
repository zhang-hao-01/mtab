package com.mtab.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.LinkFolder;
import com.mtab.entity.LinkStore;
import com.mtab.mapper.LinkStoreMapper;
import com.mtab.service.LinkFolderService;
import com.mtab.service.LinkStoreService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 书签商店服务实现
 *
 * @author zhanghao
 */
@Service
public class LinkStoreServiceImpl extends ServiceImpl<LinkStoreMapper, LinkStore> implements LinkStoreService {

    @Autowired
    private LinkFolderService linkFolderService;

    @Override
    public Page<LinkStore> list(Integer page, Integer size, String keyword, String type, Integer groupId) {
        Page<LinkStore> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<LinkStore> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LinkStore::getStatus, 1);
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.and(w -> w.like(LinkStore::getName, keyword).or().like(LinkStore::getTips, keyword));
        }
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq(LinkStore::getType, type);
        }
        wrapper.orderByDesc(LinkStore::getHot);
        return this.page(pageParam, wrapper);
    }

    @Override
    public Page<LinkStore> listManager(Integer page, Integer size, String keyword, Integer status) {
        Page<LinkStore> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<LinkStore> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(LinkStore::getName, keyword);
        }
        if (status != null) {
            wrapper.eq(LinkStore::getStatus, status);
        }
        wrapper.orderByDesc(LinkStore::getCreateTime);
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<LinkFolder> getFolder() {
        return linkFolderService.listAll();
    }

    @Override
    public List<LinkFolder> getFolderAdmin() {
        return linkFolderService.listAll();
    }

    @Override
    public void add(LinkStore linkStore, Integer userId) {
        linkStore.setUserId(userId);
        linkStore.setStatus(0);
        linkStore.setCreateTime(java.time.LocalDateTime.now());
        linkStore.setInstallNum(0);
        this.save(linkStore);
    }

    @Override
    public void addPublic(LinkStore linkStore, Integer userId) {
        linkStore.setUserId(userId);
        linkStore.setStatus(1);
        linkStore.setCreateTime(java.time.LocalDateTime.now());
        linkStore.setInstallNum(0);
        this.save(linkStore);
    }

    @Override
    public void push(Integer id, Integer userId) {
        LinkStore store = this.getById(id);
        if (store == null) {
            throw new RuntimeException("书签不存在");
        }
        if (!store.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作");
        }
        store.setStatus(1);
        this.updateById(store);
    }

    @Override
    public String getIcon(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(5000)
                    .get();
            // 尝试获取favicon
            String icon = doc.select("link[rel~=(?i)^(shortcut )?icon]").attr("href");
            if (StrUtil.isNotBlank(icon)) {
                if (icon.startsWith("//")) {
                    icon = "https:" + icon;
                } else if (icon.startsWith("/")) {
                    java.net.URI uri = new java.net.URI(url);
                    icon = uri.getScheme() + "://" + uri.getHost() + icon;
                }
                return icon;
            }
            // 默认favicon路径
            java.net.URI uri = new java.net.URI(url);
            return uri.getScheme() + "://" + uri.getHost() + "/favicon.ico";
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void installNum(Integer id) {
        LinkStore store = this.getById(id);
        if (store != null) {
            store.setInstallNum(store.getInstallNum() == null ? 1 : store.getInstallNum() + 1);
            this.updateById(store);
        }
    }

    @Override
    public void createFolder(String name, String groupIds) {
        LinkFolder folder = LinkFolder.builder()
                .name(name)
                .groupIds(groupIds)
                .sort(0)
                .build();
        linkFolderService.save(folder);
    }

    @Override
    public void moveGroup(Integer id, String groupIds) {
        LinkStore store = this.getById(id);
        if (store != null) {
            store.setGroupIds(groupIds);
            this.updateById(store);
        }
    }

    @Override
    public void moveFolder(Integer id, Integer folderId) {
        // 此方法用于文件夹移动，实际业务中可能需要调整
    }

    @Override
    public void sortFolder(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            LinkFolder folder = linkFolderService.getById(ids.get(i));
            if (folder != null) {
                folder.setSort(i);
                linkFolderService.updateById(folder);
            }
        }
    }

    @Override
    public void del(Integer id) {
        this.removeById(id);
    }

    @Override
    public List<String> domains() {
        List<LinkStore> stores = this.list(new LambdaQueryWrapper<LinkStore>()
                .select(LinkStore::getDomain)
                .isNotNull(LinkStore::getDomain));
        List<String> domains = new ArrayList<>();
        for (LinkStore store : stores) {
            if (StrUtil.isNotBlank(store.getDomain()) && !domains.contains(store.getDomain())) {
                domains.add(store.getDomain());
            }
        }
        return domains;
    }
}
