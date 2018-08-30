package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolo.wechat.entity.ArticleMessageItem;
import com.dolo.wechat.mapper.ArticleMessageItemMapper;
import com.dolo.wechat.service.IArticleMessageItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class ArticleMessageItemServiceImpl extends ServiceImpl<ArticleMessageItemMapper, ArticleMessageItem> implements IArticleMessageItemService {

    @Autowired
    private ArticleMessageItemMapper articleMessageItemMapper;

    @Override
    public List<ArticleMessageItem> getItemsByMessageId(Long articleMessageId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("article_msg_id", articleMessageId);
        return articleMessageItemMapper.selectByMap(columnMap);
    }
}
