package com.mtab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtab.entity.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * 便签Mapper
 *
 * @author zhanghao
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}
