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
 * 便签实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("note")
public class Note {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("userId")
    private Long userId;

    /** 标题 */
    @TableField("title")
    private String title;

    /** 内容 */
    @TableField("text")
    private String text;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /** 权重 */
    @TableField("weight")
    private Integer weight;

    /** 排序 */
    @TableField("sort")
    private Integer sort;
}
