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
 * 书签链接实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("link")
public class Link {

    /** 用户ID（主键） */
    @TableId(value = "userId", type = IdType.INPUT)
    private Integer userId;

    /** 链接数据（JSON格式） */
    @TableField("link")
    private String link;

    /** 更新时间 */
    @TableField("updateTime")
    private LocalDateTime updateTime;
}
