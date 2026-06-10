package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Wallpaper;

import java.util.List;
import java.util.Map;

/**
 * 壁纸服务接口
 *
 * @author zhanghao
 */
public interface WallpaperService extends IService<Wallpaper> {

    /**
     * 编辑文件夹
     */
    void editFolder(Integer id, String name);

    /**
     * 删除文件夹
     */
    void delFolder(Integer id);

    /**
     * 获取文件夹列表
     */
    List<Map<String, Object>> getFolder();

    /**
     * 客户端获取文件夹
     */
    List<Map<String, Object>> getFolderClient();

    /**
     * 获取文件夹下的壁纸
     */
    List<Wallpaper> getFolderWallpaper(Integer folderId);

    /**
     * 客户端获取文件夹壁纸
     */
    List<Wallpaper> getFolderWallpaperClient(Integer folderId);

    /**
     * 删除壁纸
     */
    void deleteWallpaper(Integer id);

    /**
     * 添加壁纸
     */
    void addWallpaper(Wallpaper wallpaper);

    /**
     * 随机壁纸
     */
    Wallpaper randomWallpaper();

    /**
     * 文件夹排序
     */
    void sortFolder(List<Integer> ids);
}
