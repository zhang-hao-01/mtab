package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface FileMapper extends BaseMapper<FileEntity> {
}
