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
 * 标签栏实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tabbar")
public class Tabbar {

    /** 用户ID（主键） */
    @TableId(value = "userId", type = IdType.INPUT)
    private Integer userId;

    /** 标签数据（JSON格式） */
    @TableField("tabs")
    private String tabs;

    /** 更新时间 */
    @TableField("updateTime")
    private LocalDateTime updateTime;
}
