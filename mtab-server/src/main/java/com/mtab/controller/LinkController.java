package com.mtab.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.History;
import com.mtab.entity.Link;
import com.mtab.service.HistoryService;
import com.mtab.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 书签链接控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private HistoryService historyService;

    /**
     * 更新书签
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String linkJson = params.get("link");
        linkService.update(userId, linkJson);
        return Result.success(null);
    }

    /**
     * 获取书签
     */
    @GetMapping("/get")
    public Result<Link> get(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(linkService.get(userId));
    }

    /**
     * 保存历史记录
     */
    @PostMapping("/history")
    public Result<Void> history(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String linkJson = params.get("link");
        linkService.history(userId, linkJson);
        return Result.success(null);
    }

    /**
     * 删除备份
     */
    @PostMapping("/delBack")
    public Result<Void> delBack(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        Long historyId = params.get("id");
        linkService.delBack(userId, historyId);
        return Result.success(null);
    }

    /**
     * 回滚书签
     */
    @PostMapping("/rollBack")
    public Result<Void> rollBack(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        Long historyId = params.get("id");
        linkService.rollBack(userId, historyId);
        return Result.success(null);
    }

    /**
     * 重置书签
     */
    @PostMapping("/reset")
    public Result<Void> reset(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        linkService.reset(userId);
        return Result.success(null);
    }

    /**
     * 获取历史记录
     */
    @GetMapping("/historyList")
    public Result<Page<History>> historyList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(historyService.getByUserId(userId, page, size));
    }
}
