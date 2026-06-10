package com.mtab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.Card;

import java.util.List;

/**
 * 小组件服务接口
 *
 * @author zhanghao
 */
public interface CardService extends IService<Card> {

    /**
     * 首页小组件列表
     */
    List<Card> index();

    /**
     * 更新安装数量
     */
    void installNum(Integer id);
}
