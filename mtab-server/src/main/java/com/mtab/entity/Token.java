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
 * Token实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("token")
public class Token {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("userId")
    private Integer userId;

    /** Token */
    @TableField("token")
    private String token;

    /** 创建时间（时间戳） */
    @TableField("createTime")
    private Integer createTime;

    /** IP地址 */
    @TableField("ip")
    private String ip;

    /** 用户代理 */
    @TableField("userAgent")
    private String userAgent;

    /** 访问令牌 */
    @TableField("accessToken")
    private String accessToken;
}
