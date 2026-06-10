package com.mtab.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码
 *
 * @author zhanghao
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(1, "操作成功"),
    ERROR(0, "操作失败"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_ERROR(400, "参数错误"),
    TOKEN_EXPIRED(401, "Token已过期"),
    TOKEN_INVALID(401, "Token无效"),
    USER_EXISTS(1001, "用户已存在"),
    USER_NOT_FOUND(1002, "用户不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    MAIL_EXISTS(1004, "邮箱已被注册"),
    LOGIN_FAIL_LIMIT(1005, "登录失败次数过多，请稍后再试"),
    ACCOUNT_DISABLED(1006, "账号已被禁用");

    private final Integer code;
    private final String msg;
}
