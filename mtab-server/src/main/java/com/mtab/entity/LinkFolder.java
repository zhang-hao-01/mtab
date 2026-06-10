package com.mtab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 书签文件夹实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("link_folder")
public class LinkFolder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 文件夹名称 */
    @TableField("name")
    private String name;

    /** 排序 */
    @TableField("sort")
    private Integer sort;

    /** 分组ID列表 */
    @TableField("groupIds")
    private String groupIds;
}
