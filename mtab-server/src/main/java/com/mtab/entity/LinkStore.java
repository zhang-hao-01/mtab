package com.mtab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 书签商店实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("link_store")
public class LinkStore {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 名称 */
    @TableField("name")
    private String name;

    /** 图标 */
    @TableField("src")
    private String src;

    /** 链接地址 */
    @TableField("url")
    private String url;

    /** 类型 */
    @TableField("type")
    private String type;

    /** 大小 */
    @TableField("size")
    private String size;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 热度 */
    @TableField("hot")
    private Long hot;

    /** 区域 */
    @TableField("area")
    private String area;

    /** 提示 */
    @TableField("tips")
    private String tips;

    /** 域名 */
    @TableField("domain")
    private String domain;

    /** 是否为应用 1-是 0-否 */
    @TableField("app")
    private Integer app;

    /** 安装数量 */
    @TableField("installNum")
    private Integer installNum;

    /** 背景颜色 */
    @TableField("bgColor")
    private String bgColor;

    /** 是否VIP 1-是 0-否 */
    @TableField("vip")
    private Integer vip;

    /** 自定义数据 */
    @TableField("custom")
    private String custom;

    /** 用户ID */
    @TableField("userId")
    private Integer userId;

    /** 状态 1-启用 0-禁用 */
    @TableField("status")
    private Integer status;

    /** 分组ID列表 */
    @TableField("groupIds")
    private String groupIds;
}
