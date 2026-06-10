-- mytab 数据库初始化脚本
-- @author zhanghao

CREATE DATABASE IF NOT EXISTS `mytab` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `mytab`;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `mail` VARCHAR(100) NOT NULL COMMENT '邮箱',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `loginIp` VARCHAR(50) DEFAULT NULL COMMENT '登录IP',
    `registerIp` VARCHAR(50) DEFAULT NULL COMMENT '注册IP',
    `manager` INT DEFAULT 0 COMMENT '是否管理员 1-是 0-否',
    `loginFailCount` INT DEFAULT 0 COMMENT '登录失败次数',
    `loginTime` DATETIME DEFAULT NULL COMMENT '登录时间',
    `qqOpenId` VARCHAR(100) DEFAULT NULL COMMENT 'QQ开放ID',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `status` INT DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    `active` DATE DEFAULT NULL COMMENT '活跃日期',
    `groupId` BIGINT DEFAULT NULL COMMENT '用户组ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mail` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- Token表
CREATE TABLE IF NOT EXISTS `token` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `userId` INT NOT NULL COMMENT '用户ID',
    `token` VARCHAR(100) NOT NULL COMMENT 'Token',
    `createTime` INT NOT NULL COMMENT '创建时间（时间戳）',
    `ip` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `userAgent` VARCHAR(500) DEFAULT NULL COMMENT '用户代理',
    `accessToken` VARCHAR(100) DEFAULT NULL COMMENT '访问令牌',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Token表';

-- 书签链接表
CREATE TABLE IF NOT EXISTS `link` (
    `userId` INT NOT NULL COMMENT '用户ID',
    `link` LONGTEXT DEFAULT NULL COMMENT '链接数据（JSON格式）',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书签链接表';

-- 书签文件夹表
CREATE TABLE IF NOT EXISTS `link_folder` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `groupIds` VARCHAR(500) DEFAULT NULL COMMENT '分组ID列表',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书签文件夹表';

-- 书签商店表
CREATE TABLE IF NOT EXISTS `link_store` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL COMMENT '名称',
    `src` VARCHAR(500) DEFAULT NULL COMMENT '图标',
    `url` VARCHAR(500) DEFAULT NULL COMMENT '链接地址',
    `type` VARCHAR(50) DEFAULT NULL COMMENT '类型',
    `size` VARCHAR(50) DEFAULT NULL COMMENT '大小',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `hot` BIGINT DEFAULT 0 COMMENT '热度',
    `area` VARCHAR(50) DEFAULT NULL COMMENT '区域',
    `tips` VARCHAR(500) DEFAULT NULL COMMENT '提示',
    `domain` VARCHAR(200) DEFAULT NULL COMMENT '域名',
    `app` INT DEFAULT 0 COMMENT '是否为应用 1-是 0-否',
    `installNum` INT DEFAULT 0 COMMENT '安装数量',
    `bgColor` VARCHAR(50) DEFAULT NULL COMMENT '背景颜色',
    `vip` INT DEFAULT 0 COMMENT '是否VIP 1-是 0-否',
    `custom` TEXT DEFAULT NULL COMMENT '自定义数据',
    `userId` INT DEFAULT NULL COMMENT '用户ID',
    `status` INT DEFAULT 0 COMMENT '状态 1-启用 0-禁用',
    `groupIds` VARCHAR(500) DEFAULT NULL COMMENT '分组ID列表',
    PRIMARY KEY (`id`),
    KEY `idx_status` (`status`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书签商店表';

-- 用户配置表
CREATE TABLE IF NOT EXISTS `config` (
    `userId` INT NOT NULL COMMENT '用户ID',
    `config` LONGTEXT DEFAULT NULL COMMENT '配置数据（JSON格式）',
    PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户配置表';

-- 标签栏表
CREATE TABLE IF NOT EXISTS `tabbar` (
    `userId` INT NOT NULL COMMENT '用户ID',
    `tabs` LONGTEXT DEFAULT NULL COMMENT '标签数据（JSON格式）',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签栏表';

-- 便签表
CREATE TABLE IF NOT EXISTS `note` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `userId` BIGINT NOT NULL COMMENT '用户ID',
    `title` VARCHAR(200) DEFAULT NULL COMMENT '标题',
    `text` LONGTEXT DEFAULT NULL COMMENT '内容',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `weight` INT DEFAULT 0 COMMENT '权重',
    `sort` INT DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='便签表';

-- 搜索引擎表
CREATE TABLE IF NOT EXISTS `search_engine` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL COMMENT '名称',
    `icon` VARCHAR(500) DEFAULT NULL COMMENT '图标',
    `url` VARCHAR(500) NOT NULL COMMENT '搜索地址',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `status` INT DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    `tips` VARCHAR(200) DEFAULT NULL COMMENT '提示',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索引擎表';

-- 用户搜索引擎表
CREATE TABLE IF NOT EXISTS `user_search_engine` (
    `userId` INT NOT NULL COMMENT '用户ID',
    `list` LONGTEXT DEFAULT NULL COMMENT '搜索引擎列表（JSON格式）',
    PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户搜索引擎表';

-- 壁纸表
CREATE TABLE IF NOT EXISTS `wallpaper` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `type` INT DEFAULT 0 COMMENT '类型 0-壁纸 1-文件夹',
    `folder` INT DEFAULT 0 COMMENT '文件夹ID',
    `mime` INT DEFAULT 0 COMMENT 'MIME类型',
    `url` VARCHAR(500) DEFAULT NULL COMMENT '图片地址',
    `cover` VARCHAR(500) DEFAULT NULL COMMENT '封面地址',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `name` VARCHAR(100) DEFAULT NULL COMMENT '名称',
    `sort` INT DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY `idx_folder` (`folder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='壁纸表';

-- 小组件（卡片）表
CREATE TABLE IF NOT EXISTS `card` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL COMMENT '名称',
    `nameEn` VARCHAR(100) DEFAULT NULL COMMENT '英文名称',
    `status` INT DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    `version` INT DEFAULT 1 COMMENT '版本',
    `tips` VARCHAR(500) DEFAULT NULL COMMENT '提示',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `src` VARCHAR(500) DEFAULT NULL COMMENT '图标',
    `url` VARCHAR(500) DEFAULT NULL COMMENT '链接',
    `window` VARCHAR(500) DEFAULT NULL COMMENT '窗口参数',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `installNum` INT DEFAULT 0 COMMENT '安装数量',
    `setting` LONGTEXT DEFAULT NULL COMMENT '设置数据（JSON格式）',
    `dictOption` LONGTEXT DEFAULT NULL COMMENT '字典选项（JSON格式）',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='小组件表';

-- 系统设置表
CREATE TABLE IF NOT EXISTS `setting` (
    `keys` VARCHAR(100) NOT NULL COMMENT '设置键',
    `value` LONGTEXT DEFAULT NULL COMMENT '设置值',
    PRIMARY KEY (`keys`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统设置表';

-- 文件表
CREATE TABLE IF NOT EXISTS `file` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `path` VARCHAR(500) NOT NULL COMMENT '文件路径',
    `userId` INT DEFAULT NULL COMMENT '用户ID',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `size` DOUBLE DEFAULT 0 COMMENT '文件大小',
    `mimeType` VARCHAR(100) DEFAULT NULL COMMENT 'MIME类型',
    `hash` VARCHAR(100) DEFAULT NULL COMMENT '文件哈希',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_hash` (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

-- 历史记录表
CREATE TABLE IF NOT EXISTS `history` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `userId` INT NOT NULL COMMENT '用户ID',
    `link` LONGTEXT DEFAULT NULL COMMENT '链接数据（JSON格式）',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='历史记录表';

-- 用户组表
CREATE TABLE IF NOT EXISTS `user_group` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL COMMENT '组名',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sort` INT DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组表';

-- 待办事项表
CREATE TABLE IF NOT EXISTS `plugins_todo` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `status` INT DEFAULT 0 COMMENT '状态 0-未完成 1-已完成',
    `userId` INT NOT NULL COMMENT '用户ID',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `expireTime` DATETIME DEFAULT NULL COMMENT '过期时间',
    `todo` TEXT NOT NULL COMMENT '待办内容',
    `weight` INT DEFAULT 0 COMMENT '权重',
    `folder` VARCHAR(50) DEFAULT NULL COMMENT '文件夹ID',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='待办事项表';

-- 待办文件夹表
CREATE TABLE IF NOT EXISTS `plugins_todo_folder` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `userId` INT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='待办文件夹表';

-- 初始化默认搜索引擎数据
INSERT INTO `search_engine` (`name`, `icon`, `url`, `sort`, `status`, `tips`) VALUES
('百度', 'https://www.baidu.com/favicon.ico', 'https://www.baidu.com/s?wd=', 0, 1, '百度一下'),
('Google', 'https://www.google.com/favicon.ico', 'https://www.google.com/search?q=', 1, 1, 'Google Search'),
('Bing', 'https://www.bing.com/favicon.ico', 'https://www.bing.com/search?q=', 2, 1, 'Microsoft Bing');

-- 初始化默认壁纸文件夹
INSERT INTO `wallpaper` (`type`, `name`, `sort`) VALUES
(1, '默认', 0),
(1, '风景', 1),
(1, '动漫', 2);

-- 初始化默认设置
INSERT INTO `setting` (`keys`, `value`) VALUES
('title', 'mTab'),
('logo', ''),
('background', ''),
('theme', 'light'),
('register', '1'),
('mail', ''),
('mailHost', ''),
('mailPort', '465'),
('mailUser', ''),
('mailPass', '');
