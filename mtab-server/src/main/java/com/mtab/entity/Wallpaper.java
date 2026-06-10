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
 * 壁纸实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wallpaper")
public class Wallpaper {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 类型 */
    @TableField("type")
    private Integer type;

    /** 文件夹ID */
    @TableField("folder")
    private Integer folder;

    /** MIME类型 */
    @TableField("mime")
    private Integer mime;

    /** 图片地址 */
    @TableField("url")
    private String url;

    /** 封面地址 */
    @TableField("cover")
    private String cover;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 名称 */
    @TableField("name")
    private String name;

    /** 排序 */
    @TableField("sort")
    private Integer sort;
}
