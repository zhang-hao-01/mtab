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
 * 用户组实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_group")
public class UserGroup {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 组名 */
    @TableField("name")
    private String name;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 排序 */
    @TableField("sort")
    private Integer sort;
}
