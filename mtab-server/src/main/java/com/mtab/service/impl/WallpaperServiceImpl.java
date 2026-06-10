package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.Wallpaper;
import com.mtab.mapper.WallpaperMapper;
import com.mtab.service.WallpaperService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 壁纸服务实现
 *
 * @author zhanghao
 */
@Service
public class WallpaperServiceImpl extends ServiceImpl<WallpaperMapper, Wallpaper> implements WallpaperService {

    @Override
    public void editFolder(Integer id, String name) {
        // 文件夹编辑逻辑 - 在wallpaper表中folder=0的记录代表文件夹
    }

    @Override
    public void delFolder(Integer id) {
        // 删除文件夹及其下所有壁纸
        this.remove(new LambdaQueryWrapper<Wallpaper>().eq(Wallpaper::getFolder, id));
    }

    @Override
    public List<Map<String, Object>> getFolder() {
        List<Wallpaper> folders = this.list(new LambdaQueryWrapper<Wallpaper>()
                .eq(Wallpaper::getType, 1)
                .orderByAsc(Wallpaper::getSort));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Wallpaper folder : folders) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", folder.getId());
            map.put("name", folder.getName());
            map.put("sort", folder.getSort());
            // 获取文件夹下壁纸数量
            long count = this.count(new LambdaQueryWrapper<Wallpaper>()
                    .eq(Wallpaper::getFolder, folder.getId())
                    .eq(Wallpaper::getType, 0));
            map.put("count", count);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getFolderClient() {
        return getFolder();
    }

    @Override
    public List<Wallpaper> getFolderWallpaper(Integer folderId) {
        return this.list(new LambdaQueryWrapper<Wallpaper>()
                .eq(Wallpaper::getFolder, folderId)
                .eq(Wallpaper::getType, 0)
                .orderByAsc(Wallpaper::getSort));
    }

    @Override
    public List<Wallpaper> getFolderWallpaperClient(Integer folderId) {
        return getFolderWallpaper(folderId);
    }

    @Override
    public void deleteWallpaper(Integer id) {
        this.removeById(id);
    }

    @Override
    public void addWallpaper(Wallpaper wallpaper) {
        wallpaper.setCreateTime(LocalDateTime.now());
        this.save(wallpaper);
    }

    @Override
    public Wallpaper randomWallpaper() {
        long count = this.count(new LambdaQueryWrapper<Wallpaper>().eq(Wallpaper::getType, 0));
        if (count == 0) {
            return null;
        }
        int index = (int) (Math.random() * count);
        List<Wallpaper> list = this.list(new LambdaQueryWrapper<Wallpaper>()
                .eq(Wallpaper::getType, 0)
                .last("LIMIT 1 OFFSET " + index));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void sortFolder(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Wallpaper wallpaper = this.getById(ids.get(i));
            if (wallpaper != null) {
                wallpaper.setSort(i);
                this.updateById(wallpaper);
            }
        }
    }
}
