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
 * 用户搜索引擎实体
 *
 * @author zhanghao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_search_engine")
public class UserSearchEngine {

    /** 用户ID（主键） */
    @TableId(value = "userId", type = IdType.INPUT)
    private Integer userId;

    /** 搜索引擎列表（JSON格式） */
    @TableField("list")
    private String list;
}
