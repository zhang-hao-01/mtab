package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.Config;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户配置Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
}
