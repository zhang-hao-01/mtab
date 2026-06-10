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
 * 小组件（卡片）实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("card")
public class Card {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 名称 */
    @TableField("name")
    private String name;

    /** 英文名称 */
    @TableField("nameEn")
    private String nameEn;

    /** 状态 1-启用 0-禁用 */
    @TableField("status")
    private Integer status;

    /** 版本 */
    @TableField("version")
    private Integer version;

    /** 提示 */
    @TableField("tips")
    private String tips;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 图标 */
    @TableField("src")
    private String src;

    /** 链接 */
    @TableField("url")
    private String url;

    /** 窗口参数 */
    @TableField("window")
    private String window;

    /** 更新时间 */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /** 安装数量 */
    @TableField("installNum")
    private Integer installNum;

    /** 设置数据（JSON格式） */
    @TableField("setting")
    private String setting;

    /** 字典选项（JSON格式） */
    @TableField("dictOption")
    private String dictOption;
}
