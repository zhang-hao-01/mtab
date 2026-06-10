package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.Setting;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统设置Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {
}
