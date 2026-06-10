package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.Card;
import com.mtab.entity.User;
import com.mtab.service.CardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 小组件控制器
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * 小组件列表
     */
    @GetMapping("/index")
    public Result<List<Card>> index() {
        return Result.success(cardService.index());
    }

    /**
     * 更新安装数量
     */
    @PostMapping("/installNum")
    public Result<Void> installNum(@RequestBody Map<String, Integer> params) {
        Integer id = params.get("id");
        cardService.installNum(id);
        return Result.success(null);
    }
}
