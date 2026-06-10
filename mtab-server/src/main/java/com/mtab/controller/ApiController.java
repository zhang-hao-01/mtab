package com.mtab.controller;

import com.mtab.common.Constants;
import com.mtab.common.Result;
import com.mtab.entity.User;
import com.mtab.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API控制器 - 提供通用API接口
 *
 * @author zhanghao
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * 获取当前用户信息（需要登录）
     */
    @GetMapping("/user/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.ATTR_USER);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }
}
