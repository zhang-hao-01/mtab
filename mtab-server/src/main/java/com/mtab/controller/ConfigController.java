package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.Config;
import com.mtab.service.ConfigService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户配置控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 更新配置
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String configJson = params.get("config");
        configService.update(userId, configJson);
        return Result.success(null);
    }

    /**
     * 获取配置
     */
    @GetMapping("/get")
    public Result<Config> get(@RequestParam(required = false) Integer userId, HttpServletRequest request) {
        // 如果传了userId参数则使用传入的，否则使用当前登录用户
        if (userId == null) {
            userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        }
        return Result.success(configService.get(userId));
    }
}
