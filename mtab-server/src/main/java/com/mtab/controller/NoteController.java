package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.Note;
import com.mtab.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 便签控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * 获取便签列表
     */
    @GetMapping("/get")
    public Result<List<Note>> get(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(noteService.get(userId.longValue()));
    }

    /**
     * 获取便签内容
     */
    @GetMapping("/getText")
    public Result<Note> getText(@RequestParam Long id, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        return Result.success(noteService.getText(id, userId.longValue()));
    }

    /**
     * 添加便签
     */
    @PostMapping("/add")
    public Result<Note> add(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        String title = params.get("title");
        String text = params.get("text");
        return Result.success(noteService.add(userId.longValue(), title, text));
    }

    /**
     * 更新便签
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        Long id = Long.valueOf(params.get("id").toString());
        String title = (String) params.get("title");
        String text = (String) params.get("text");
        noteService.update(id, userId.longValue(), title, text);
        return Result.success(null);
    }

    /**
     * 删除便签
     */
    @PostMapping("/del")
    public Result<Void> del(@RequestBody Map<String, Long> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        Long id = params.get("id");
        noteService.del(id, userId.longValue());
        return Result.success(null);
    }

    /**
     * 设置权重
     */
    @PostMapping("/setWeight")
    public Result<Void> setWeight(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(Constants.ATTR_USER_ID);
        Long id = Long.valueOf(params.get("id").toString());
        Integer weight = Integer.valueOf(params.get("weight").toString());
        noteService.setWeight(id, weight, userId.longValue());
        return Result.success(null);
    }

    /**
     * 排序便签
     */
    @PostMapping("/sort")
    public Result<Void> sort(@RequestBody List<Long> ids) {
        noteService.sort(ids);
        return Result.success(null);
    }
}
