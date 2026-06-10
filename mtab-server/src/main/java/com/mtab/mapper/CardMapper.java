package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.Card;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小组件Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface CardMapper extends BaseMapper<Card> {
}
