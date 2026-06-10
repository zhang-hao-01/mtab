package com.mtab.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.FileEntity;
import com.mtab.entity.User;
import com.mtab.entity.UserGroup;
import com.mtab.service.FileService;
import com.mtab.service.UserGroupService;
import com.mtab.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private FileService fileService;

    /**
     * 获取用户列表
     */
    @GetMapping("/user/list")
    public Result<Page<com.mtab.entity.User>> userList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Page<com.mtab.entity.User> pageParam = new Page<>(page, size);
        return Result.success(userService.page(pageParam));
    }

    /**
     * 禁用/启用用户
     */
    @PostMapping("/user/setStatus")
    public Result<Void> setUserStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Integer userId = Integer.valueOf(params.get("userId").toString());
        Integer status = Integer.valueOf(params.get("status").toString());
        com.mtab.entity.User targetUser = userService.getById(userId);
        if (targetUser != null) {
            targetUser.setStatus(status);
            userService.updateById(targetUser);
        }
        return Result.success(null);
    }

    /**
     * 设置管理员
     */
    @PostMapping("/user/setManager")
    public Result<Void> setManager(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Integer userId = Integer.valueOf(params.get("userId").toString());
        Integer manager = Integer.valueOf(params.get("manager").toString());
        com.mtab.entity.User targetUser = userService.getById(userId);
        if (targetUser != null) {
            targetUser.setManager(manager);
            userService.updateById(targetUser);
        }
        return Result.success(null);
    }

    /**
     * 删除用户
     */
    @PostMapping("/user/del")
    public Result<Void> delUser(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        userService.removeById(params.get("userId"));
        return Result.success(null);
    }

    /**
     * 获取用户组列表
     */
    @GetMapping("/group/list")
    public Result<List<UserGroup>> groupList(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        return Result.success(userGroupService.list());
    }

    /**
     * 创建用户组
     */
    @PostMapping("/group/create")
    public Result<Void> groupCreate(@RequestBody Map<String, String> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        userGroupService.create(params.get("name"));
        return Result.success(null);
    }

    /**
     * 编辑用户组
     */
    @PostMapping("/group/edit")
    public Result<Void> groupEdit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Long id = Long.valueOf(params.get("id").toString());
        String name = (String) params.get("name");
        userGroupService.edit(id, name);
        return Result.success(null);
    }

    /**
     * 删除用户组
     */
    @PostMapping("/group/del")
    public Result<Void> groupDel(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        userGroupService.delete(params.get("id"));
        return Result.success(null);
    }

    /**
     * 排序用户组
     */
    @PostMapping("/group/sort")
    public Result<Void> groupSort(@RequestBody List<Long> ids, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        userGroupService.sort(ids);
        return Result.success(null);
    }

    /**
     * 文件列表
     */
    @GetMapping("/file/list")
    public Result<Page<FileEntity>> fileList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        return Result.success(fileService.list(null, page, size));
    }

    /**
     * 删除文件
     */
    @PostMapping("/file/del")
    public Result<Void> fileDel(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user == null || !Integer.valueOf(Constants.ROLE_ADMIN).equals(user.getManager())) {
            return Result.error("没有权限");
        }
        Long id = Long.valueOf(params.get("id").toString());
        fileService.del(id, null);
        return Result.success(null);
    }
}
