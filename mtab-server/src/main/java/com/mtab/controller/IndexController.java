package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.Card;
import com.mtab.entity.SearchEngine;
import com.mtab.entity.Setting;
import com.mtab.entity.Wallpaper;
import com.mtab.service.CardService;
import com.mtab.service.SearchEngineService;
import com.mtab.service.SettingService;
import com.mtab.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    private SearchEngineService searchEngineService;

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private CardService cardService;

    @Autowired
    private SettingService settingService;

    /**
     * 获取首页初始化数据
     */
    @GetMapping("/init")
    public Result<Map<String, Object>> init() {
        Map<String, Object> data = new HashMap<>();
        data.put("searchEngine", searchEngineService.index());
        data.put("wallpaper", wallpaperService.randomWallpaper());
        data.put("card", cardService.index());
        data.put("setting", settingService.getSetting());
        return Result.success(data);
    }

    /**
     * 获取搜索引擎列表
     */
    @GetMapping("/searchEngine")
    public Result<List<SearchEngine>> searchEngine() {
        return Result.success(searchEngineService.index());
    }

    /**
     * 随机壁纸
     */
    @GetMapping("/randomWallpaper")
    public Result<Wallpaper> randomWallpaper() {
        return Result.success(wallpaperService.randomWallpaper());
    }

    /**
     * 获取小组件列表
     */
    @GetMapping("/card")
    public Result<List<Card>> card() {
        return Result.success(cardService.index());
    }

    /**
     * 获取系统设置
     */
    @GetMapping("/setting")
    public Result<Map<String, String>> setting() {
        return Result.success(settingService.getSetting());
    }
}
