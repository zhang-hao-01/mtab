package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.Tabbar;
import com.mtab.service.TabbarService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 标签栏控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/tabbar")
public class TabbarController {

    @Autowired
    private TabbarService tabbarService;

    /**
     * 更新标签栏
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String tabsJson = params.get("tabs");
        tabbarService.update(userId, tabsJson);
        return Result.success(null);
    }

    /**
     * 获取标签栏
     */
    @GetMapping("/get")
    public Result<Tabbar> get(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(tabbarService.get(userId));
    }
}
