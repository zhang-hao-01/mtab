package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.History;
import org.apache.ibatis.annotations.Mapper;

/**
 * 历史记录Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface HistoryMapper extends BaseMapper<History> {
}
