package com.dolo.wechat.service;

import com.dolo.wechat.entity.ArticleMessageItem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IArticleMessageItemService extends IService<ArticleMessageItem> {

    public List<ArticleMessageItem> getItemsByMessageId(Long articleMessageId);
}
