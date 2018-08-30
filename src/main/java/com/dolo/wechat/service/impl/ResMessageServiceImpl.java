package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.mapper.ResMessageMapper;
import com.dolo.wechat.service.IResMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class ResMessageServiceImpl extends ServiceImpl<ResMessageMapper, ResMessage> implements IResMessageService {

    @Autowired
    private ResMessageMapper resMessageMapper;

    @Override
    public ResMessage getResMessageByAccountIdAndMessageKey(String keyWords, String accountId) {
        ResMessage resMessage = new ResMessage();
        resMessage.setAccountId(accountId);
        resMessage.setKeyWords(keyWords);
        return resMessageMapper.selectOne(resMessage);
    }
}
