package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.FileEntity;
import com.mtab.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件列表
     */
    @GetMapping("/list")
    public Result<Object> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(fileService.list(userId, page, size));
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public Result<FileEntity> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(fileService.addFile(file, userId));
    }

    /**
     * 删除文件
     */
    @PostMapping("/del")
    public Result<Void> del(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        fileService.del(params.get("id"), userId);
        return Result.success(null);
    }
}
