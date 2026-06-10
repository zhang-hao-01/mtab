package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.Note;
import com.mtab.mapper.NoteMapper;
import com.mtab.service.NoteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 便签服务实现
 *
 * @author zhanghao
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Override
    public List<Note> get(Long userId) {
        return this.list(new LambdaQueryWrapper<Note>()
                .eq(Note::getUserId, userId)
                .orderByAsc(Note::getSort)
                .orderByDesc(Note::getWeight));
    }

    @Override
    public void sort(List<Long> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Note note = this.getById(ids.get(i));
            if (note != null) {
                note.setSort(i);
                this.updateById(note);
            }
        }
    }

    @Override
    public Note getText(Long id, Long userId) {
        return this.getOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, id)
                .eq(Note::getUserId, userId));
    }

    @Override
    public void setWeight(Long id, Integer weight, Long userId) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, id)
                .eq(Note::getUserId, userId));
        if (note != null) {
            note.setWeight(weight);
            this.updateById(note);
        }
    }

    @Override
    public void del(Long id, Long userId) {
        this.remove(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, id)
                .eq(Note::getUserId, userId));
    }

    @Override
    public Note add(Long userId, String title, String text) {
        Note note = Note.builder()
                .userId(userId)
                .title(title)
                .text(text)
                .weight(0)
                .sort(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(note);
        return note;
    }

    @Override
    public void update(Long id, Long userId, String title, String text) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, id)
                .eq(Note::getUserId, userId));
        if (note == null) {
            throw new RuntimeException("便签不存在");
        }
        if (title != null) {
            note.setTitle(title);
        }
        if (text != null) {
            note.setText(text);
        }
        note.setUpdateTime(LocalDateTime.now());
        this.updateById(note);
    }
}
