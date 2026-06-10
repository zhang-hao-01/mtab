package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.SearchEngine;
import com.mtab.entity.User;
import com.mtab.entity.UserSearchEngine;
import com.mtab.service.SearchEngineService;
import com.mtab.service.UserSearchEngineService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 搜索引擎控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/searchEngine")
public class SearchEngineController {

    @Autowired
    private SearchEngineService searchEngineService;

    @Autowired
    private UserSearchEngineService userSearchEngineService;

    /**
     * 搜索引擎列表
     */
    @GetMapping("/list")
    public Result<List<SearchEngine>> list() {
        return Result.success(searchEngineService.index());
    }

    /**
     * 管理列表
     */
    @GetMapping("/manager")
    public Result<List<SearchEngine>> manager(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        return Result.success(searchEngineService.list());
    }

    /**
     * 添加搜索引擎
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody SearchEngine searchEngine, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        searchEngineService.add(searchEngine);
        return Result.success(null);
    }

    /**
     * 删除搜索引擎
     */
    @PostMapping("/del")
    public Result<Void> del(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        searchEngineService.del(params.get("id"));
        return Result.success(null);
    }

    /**
     * 获取搜索引擎详情
     */
    @GetMapping("/get")
    public Result<SearchEngine> get(@RequestParam Integer id) {
        return Result.success(searchEngineService.searchEngine(id));
    }

    /**
     * 保存用户自定义搜索引擎
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String listJson = params.get("list");
        searchEngineService.saveSearchEngine(userId, listJson);
        return Result.success(null);
    }

    /**
     * 获取用户自定义搜索引擎
     */
    @GetMapping("/userList")
    public Result<UserSearchEngine> userList(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(userSearchEngineService.getByUserId(userId));
    }

    /**
     * 排序搜索引擎
     */
    @PostMapping("/sort")
    public Result<Void> sort(@RequestBody List<Integer> ids, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        searchEngineService.sort(ids);
        return Result.success(null);
    }
}
