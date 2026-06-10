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
 * 待办事项实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("plugins_todo")
public class PluginsTodo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 状态 0-未完成 1-已完成 */
    @TableField("status")
    private Integer status;

    /** 用户ID */
    @TableField("userId")
    private Integer userId;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 过期时间 */
    @TableField("expireTime")
    private LocalDateTime expireTime;

    /** 待办内容 */
    @TableField("todo")
    private String todo;

    /** 权重 */
    @TableField("weight")
    private Integer weight;

    /** 文件夹ID */
    @TableField("folder")
    private String folder;
}
