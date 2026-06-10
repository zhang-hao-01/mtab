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
 * 待办文件夹实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("plugins_todo_folder")
public class PluginsTodoFolder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 用户ID */
    @TableField("userId")
    private Integer userId;

    /** 文件夹名称 */
    @TableField("name")
    private String name;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;
}
