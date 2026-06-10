package com.mtab.interceptor;

import com.mtab.common.Constants;
import com.mtab.entity.Token;
import com.mtab.entity.User;
import com.mtab.service.TokenService;
import com.mtab.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 认证拦截器
 *
 * @author zhanghao
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头获取用户ID和Token
        String userIdStr = request.getHeader(Constants.HEADER_USER_ID);
        String tokenStr = request.getHeader(Constants.HEADER_TOKEN);

        // 如果请求头中没有，尝试从Cookie获取
        if (userIdStr == null || tokenStr == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (Constants.HEADER_USER_ID.equals(cookie.getName())) {
                        userIdStr = cookie.getValue();
                    }
                    if (Constants.HEADER_TOKEN.equals(cookie.getName())) {
                        tokenStr = cookie.getValue();
                    }
                }
            }
        }

        if (userIdStr == null || tokenStr == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"未授权，请先登录\",\"data\":null}");
            return false;
        }

        try {
            Integer userId = Integer.parseInt(userIdStr);

            // 验证Token
            Token token = tokenService.validateToken(userId, tokenStr);
            if (token == null) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":0,\"msg\":\"Token已过期或无效\",\"data\":null}");
                return false;
            }

            // 如果Token被刷新了，将新Token返回给客户端
            if (!tokenStr.equals(token.getToken())) {
                response.setHeader(Constants.HEADER_USER_ID, String.valueOf(userId));
                response.setHeader(Constants.HEADER_TOKEN, token.getToken());
            }

            // 获取用户信息
            User user = userService.getById(userId);
            if (user == null) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":0,\"msg\":\"用户不存在\",\"data\":null}");
                return false;
            }

            // 检查用户状态
            if (Integer.valueOf(Constants.STATUS_DISABLE).equals(user.getStatus())) {
                response.setStatus(403);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":0,\"msg\":\"账号已被禁用\",\"data\":null}");
                return false;
            }

            // 将用户信息存入请求属性
            request.setAttribute(Constants.ATTR_USER, user);
            request.setAttribute(Constants.ATTR_USER_ID, userId);

            return true;
        } catch (NumberFormatException e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":0,\"msg\":\"无效的用户ID\",\"data\":null}");
            return false;
        }
    }
}
