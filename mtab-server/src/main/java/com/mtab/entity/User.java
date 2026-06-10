package com.mtab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 头像 */
    @TableField("avatar")
    private String avatar;

    /** 邮箱 */
    @TableField("mail")
    private String mail;

    /** 密码 */
    @TableField("password")
    private String password;

    /** 创建时间 */
    @TableField("createTime")
    private LocalDateTime createTime;

    /** 登录IP */
    @TableField("loginIp")
    private String loginIp;

    /** 注册IP */
    @TableField("registerIp")
    private String registerIp;

    /** 是否管理员 1-是 0-否 */
    @TableField("manager")
    private Integer manager;

    /** 登录失败次数 */
    @TableField("loginFailCount")
    private Integer loginFailCount;

    /** 登录时间 */
    @TableField("loginTime")
    private LocalDateTime loginTime;

    /** QQ开放ID */
    @TableField("qqOpenId")
    private String qqOpenId;

    /** 昵称 */
    @TableField("nickname")
    private String nickname;

    /** 状态 1-启用 0-禁用 */
    @TableField("status")
    private Integer status;

    /** 活跃日期 */
    @TableField("active")
    private LocalDate active;

    /** 用户组ID */
    @TableField("groupId")
    private Long groupId;
}
