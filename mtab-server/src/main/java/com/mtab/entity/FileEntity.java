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
 * 文件实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("file")
public class FileEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 文件路径 */
    @TableField("path")
    private String path;

    /** 用户ID */
    @TableField("userId")
    private Integer userId;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 文件大小 */
    @TableField("size")
    private Double size;

    /** MIME类型 */
    @TableField("mimeType")
    private String mimeType;

    /** 文件哈希 */
    @TableField("hash")
    private String hash;
}
