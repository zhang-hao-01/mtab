package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.User;
import com.mtab.service.SettingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统设置控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * 获取设置
     */
    @GetMapping("/get")
    public Result<Map<String, String>> get() {
        return Result.success(settingService.getSetting());
    }

    /**
     * 保存设置（管理员）
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Map<String, String> settings, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        settingService.saveSetting(settings);
        return Result.success(null);
    }

    /**
     * 刷新缓存（管理员）
     */
    @PostMapping("/refreshCache")
    public Result<Void> refreshCache(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        settingService.refreshCache();
        return Result.success(null);
    }
}
