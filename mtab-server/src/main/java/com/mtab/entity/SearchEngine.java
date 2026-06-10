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
 * 搜索引擎实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("search_engine")
public class SearchEngine {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 名称 */
    @TableField("name")
    private String name;

    /** 图标 */
    @TableField("icon")
    private String icon;

    /** 搜索地址 */
    @TableField("url")
    private String url;

    /** 排序 */
    @TableField("sort")
    private Integer sort;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 状态 1-启用 0-禁用 */
    @TableField("status")
    private Integer status;

    /** 提示 */
    @TableField("tips")
    private String tips;
}
