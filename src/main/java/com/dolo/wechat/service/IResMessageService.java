package com.dolo.wechat.service;

import com.dolo.wechat.entity.ResMessage;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IResMessageService extends IService<ResMessage> {

    ResMessage getResMessageByAccountIdAndMessageKey(String keyWords, String accountId);

    ResMessage getResMessageByAccountIdAndId(Long id, String accountId);

    ResMessage getResMessageByAccountIdAndMessageName(String mesageName, String accountId);

}
