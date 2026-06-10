package com.mtab.common;

/**
 * 系统常量
 *
 * @author zhanghao
 */
public class Constants {

    /** 管理员角色标识 */
    public static final int ROLE_ADMIN = 1;

    /** 普通用户角色标识 */
    public static final int ROLE_USER = 0;

    /** 账号启用状态 */
    public static final int STATUS_ENABLE = 1;

    /** 账号禁用状态 */
    public static final int STATUS_DISABLE = 0;

    /** Token过期天数 */
    public static final int TOKEN_EXPIRE_DAYS = 15;

    /** Token刷新天数（10-15天内使用自动刷新） */
    public static final int TOKEN_REFRESH_DAYS = 10;

    /** 最大登录失败次数 */
    public static final int MAX_LOGIN_FAIL_COUNT = 5;

    /** 登录失败锁定时间（秒） */
    public static final long LOGIN_FAIL_LOCK_TIME = 1800L;

    /** 请求头 - 用户ID */
    public static final String HEADER_USER_ID = "Userid";

    /** 请求头 - Token */
    public static final String HEADER_TOKEN = "Token";

    /** 请求属性 - 当前用户 */
    public static final String ATTR_USER = "currentUser";

    /** 请求属性 - 当前用户ID */
    public static final String ATTR_USER_ID = "currentUserId";

    /** Redis缓存前缀 */
    public static final String REDIS_SETTING_PREFIX = "mtab:setting:";

    /** Redis缓存前缀 - 搜索引擎 */
    public static final String REDIS_SEARCH_ENGINE_PREFIX = "mtab:searchEngine:";

    /** Redis缓存前缀 - 小组件 */
    public static final String REDIS_CARD_PREFIX = "mtab:card:";

    private Constants() {
    }
}
