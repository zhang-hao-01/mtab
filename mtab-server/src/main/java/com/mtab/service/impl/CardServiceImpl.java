package com.mtab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.Card;
import com.mtab.mapper.CardMapper;
import com.mtab.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小组件服务实现
 *
 * @author zhanghao
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {

    @Override
    public List<Card> index() {
        return this.list(new LambdaQueryWrapper<Card>()
                .eq(Card::getStatus, 1));
    }

    @Override
    public void installNum(Integer id) {
        Card card = this.getById(id);
        if (card != null) {
            card.setInstallNum(card.getInstallNum() == null ? 1 : card.getInstallNum() + 1);
            this.updateById(card);
        }
    }
}
