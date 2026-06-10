package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.PluginsTodo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待办事项Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface PluginsTodoMapper extends BaseMapper<PluginsTodo> {
}
