package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.User;
import com.mtab.entity.Wallpaper;
import com.mtab.service.WallpaperService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 壁纸控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/wallpaper")
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    /**
     * 获取文件夹列表
     */
    @GetMapping("/getFolder")
    public Result<List<Map<String, Object>>> getFolder() {
        return Result.success(wallpaperService.getFolder());
    }

    /**
     * 客户端获取文件夹
     */
    @GetMapping("/getFolderClient")
    public Result<List<Map<String, Object>>> getFolderClient() {
        return Result.success(wallpaperService.getFolderClient());
    }

    /**
     * 获取文件夹下的壁纸
     */
    @GetMapping("/getFolderWallpaper")
    public Result<List<Wallpaper>> getFolderWallpaper(@RequestParam Integer folderId) {
        return Result.success(wallpaperService.getFolderWallpaper(folderId));
    }

    /**
     * 客户端获取文件夹壁纸
     */
    @GetMapping("/getFolderWallpaperClient")
    public Result<List<Wallpaper>> getFolderWallpaperClient(@RequestParam Integer folderId) {
        return Result.success(wallpaperService.getFolderWallpaperClient(folderId));
    }

    /**
     * 随机壁纸
     */
    @GetMapping("/random")
    public Result<Wallpaper> randomWallpaper() {
        return Result.success(wallpaperService.randomWallpaper());
    }

    /**
     * 编辑文件夹（管理员）
     */
    @PostMapping("/editFolder")
    public Result<Void> editFolder(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Integer id = Integer.valueOf(params.get("id").toString());
        String name = (String) params.get("name");
        wallpaperService.editFolder(id, name);
        return Result.success(null);
    }

    /**
     * 删除文件夹（管理员）
     */
    @PostMapping("/delFolder")
    public Result<Void> delFolder(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        wallpaperService.delFolder(params.get("id"));
        return Result.success(null);
    }

    /**
     * 删除壁纸（管理员）
     */
    @PostMapping("/deleteWallpaper")
    public Result<Void> deleteWallpaper(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        wallpaperService.deleteWallpaper(params.get("id"));
        return Result.success(null);
    }

    /**
     * 添加壁纸（管理员）
     */
    @PostMapping("/addWallpaper")
    public Result<Void> addWallpaper(@RequestBody Wallpaper wallpaper, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        wallpaperService.addWallpaper(wallpaper);
        return Result.success(null);
    }

    /**
     * 文件夹排序（管理员）
     */
    @PostMapping("/sortFolder")
    public Result<Void> sortFolder(@RequestBody List<Integer> ids, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        wallpaperService.sortFolder(ids);
        return Result.success(null);
    }
}
