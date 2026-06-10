package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.SearchEngine;
import com.mtab.entity.UserSearchEngine;
import com.mtab.mapper.SearchEngineMapper;
import com.mtab.service.SearchEngineService;
import com.mtab.service.UserSearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 搜索引擎服务实现
 *
 * @author zhanghao
 */
@Service
public class SearchEngineServiceImpl extends ServiceImpl<SearchEngineMapper, SearchEngine> implements SearchEngineService {

    @Autowired
    private UserSearchEngineService userSearchEngineService;

    @Override
    public List<SearchEngine> index() {
        return this.list(new LambdaQueryWrapper<SearchEngine>()
                .eq(SearchEngine::getStatus, 1)
                .orderByAsc(SearchEngine::getSort));
    }

    @Override
    public List<SearchEngine> list() {
        return this.list(new LambdaQueryWrapper<SearchEngine>()
                .orderByAsc(SearchEngine::getSort));
    }

    @Override
    public void add(SearchEngine searchEngine) {
        searchEngine.setCreateTime(LocalDateTime.now());
        searchEngine.setStatus(searchEngine.getStatus() == null ? 1 : searchEngine.getStatus());
        this.save(searchEngine);
    }

    @Override
    public void del(Integer id) {
        this.removeById(id);
    }

    @Override
    public SearchEngine searchEngine(Integer id) {
        return this.getById(id);
    }

    @Override
    public void saveSearchEngine(Integer userId, String listJson) {
        UserSearchEngine userSearchEngine = userSearchEngineService.getByUserId(userId);
        if (userSearchEngine == null) {
            userSearchEngine = UserSearchEngine.builder()
                    .userId(userId)
                    .list(listJson)
                    .build();
            userSearchEngineService.save(userSearchEngine);
        } else {
            userSearchEngine.setList(listJson);
            userSearchEngineService.updateById(userSearchEngine);
        }
    }

    @Override
    public void sort(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            SearchEngine engine = this.getById(ids.get(i));
            if (engine != null) {
                engine.setSort(i);
                this.updateById(engine);
            }
        }
    }
}
