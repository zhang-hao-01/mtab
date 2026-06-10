package com.mtab.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.LinkFolder;
import com.mtab.entity.LinkStore;
import com.mtab.entity.User;
import com.mtab.service.LinkStoreService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 书签商店控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/linkStore")
public class LinkStoreController {

    @Autowired
    private LinkStoreService linkStoreService;

    /**
     * 用户列表
     */
    @GetMapping("/list")
    public Result<Page<LinkStore>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer groupId) {
        return Result.success(linkStoreService.list(page, size, keyword, type, groupId));
    }

    /**
     * 管理员列表
     */
    @GetMapping("/listManager")
    public Result<Page<LinkStore>> listManager(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        return Result.success(linkStoreService.listManager(page, size, keyword, status));
    }

    /**
     * 获取文件夹
     */
    @GetMapping("/getFolder")
    public Result<List<LinkFolder>> getFolder() {
        return Result.success(linkStoreService.getFolder());
    }

    /**
     * 管理员获取文件夹
     */
    @GetMapping("/getFolderAdmin")
    public Result<List<LinkFolder>> getFolderAdmin(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        return Result.success(linkStoreService.getFolderAdmin());
    }

    /**
     * 添加书签
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody LinkStore linkStore, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        linkStoreService.add(linkStore, userId);
        return Result.success(null);
    }

    /**
     * 添加公开书签（管理员）
     */
    @PostMapping("/addPublic")
    public Result<Void> addPublic(@RequestBody LinkStore linkStore, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        linkStoreService.addPublic(linkStore, userId);
        return Result.success(null);
    }

    /**
     * 推送书签
     */
    @PostMapping("/push")
    public Result<Void> push(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        linkStoreService.push(params.get("id"), userId);
        return Result.success(null);
    }

    /**
     * 获取图标
     */
    @GetMapping("/getIcon")
    public Result<String> getIcon(@RequestParam String url) {
        return Result.success(linkStoreService.getIcon(url));
    }

    /**
     * 更新安装数量
     */
    @PostMapping("/installNum")
    public Result<Void> installNum(@RequestBody Map<String, Integer> params) {
        linkStoreService.installNum(params.get("id"));
        return Result.success(null);
    }

    /**
     * 创建文件夹（管理员）
     */
    @PostMapping("/createFolder")
    public Result<Void> createFolder(@RequestBody Map<String, String> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        linkStoreService.createFolder(params.get("name"), params.get("groupIds"));
        return Result.success(null);
    }

    /**
     * 移动分组（管理员）
     */
    @PostMapping("/moveGroup")
    public Result<Void> moveGroup(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Integer id = Integer.valueOf(params.get("id").toString());
        String groupIds = (String) params.get("groupIds");
        linkStoreService.moveGroup(id, groupIds);
        return Result.success(null);
    }

    /**
     * 排序文件夹（管理员）
     */
    @PostMapping("/sortFolder")
    public Result<Void> sortFolder(@RequestBody List<Integer> ids, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        linkStoreService.sortFolder(ids);
        return Result.success(null);
    }

    /**
     * 删除书签（管理员）
     */
    @PostMapping("/del")
    public Result<Void> del(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        linkStoreService.del(params.get("id"));
        return Result.success(null);
    }

    /**
     * 获取域名列表
     */
    @GetMapping("/domains")
    public Result<List<String>> domains() {
        return Result.success(linkStoreService.domains());
    }
}
