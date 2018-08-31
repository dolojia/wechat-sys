package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.mapper.ResMessageMapper;
import com.dolo.wechat.service.IResMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResMessage getResMessageByAccountIdAndId(Long id, String accountId) {
        ResMessage resMessage = new ResMessage();
        resMessage.setAccountId(accountId);
        resMessage.setId(id);
        return resMessageMapper.selectOne(resMessage);
    }

    @Override
    public ResMessage getResMessageByAccountIdAndMessageName(String mesageName, String accountId) {
        ResMessage resMessage = new ResMessage();
        resMessage.setAccountId(accountId);
        resMessage.setMsgName(mesageName);
        return resMessageMapper.selectOne(resMessage);
    }

    private List<ResMessage> getResMessageListByNameAndKeyWords(String mesageName, String keyWords, String accountId) {
        ResMessage resMessage = new ResMessage();
        resMessage.setAccountId(accountId);
        resMessage.setMsgName(mesageName);
        resMessage.setKeyWords(keyWords);
        Map<String, Object> map = new HashMap<>();
        map.put("msg_name",mesageName);
        map.put("key_words",keyWords);
        map.put("account_id",accountId);
        return resMessageMapper.selectByMap(map);
    }

    @Override
    public void checkResMessage(String id, String msgName, String keyWords, String accountId) {
        List<ResMessage> list = this.getResMessageListByNameAndKeyWords(msgName,keyWords,accountId);
        String errorMessage = "";
        // 新增判断
        if (StringUtils.isEmpty(id))
        {
            if (list.size() > 0)
            {
                for (ResMessage resMessage : list)
                {
                    if (StringUtils.isNotEmpty(resMessage.getMsgName()) &&
                            resMessage.getMsgName().equalsIgnoreCase(msgName) &&
                            resMessage.getAccountId().equalsIgnoreCase(accountId))
                    {
                        errorMessage = "消息名称已存在！";
                        break;
                    }
                    if(StringUtils.isNotEmpty(keyWords)){
                        if (StringUtils.isNotEmpty(resMessage.getKeyWords()) &&
                                resMessage.getKeyWords().equalsIgnoreCase(keyWords) &&
                                resMessage.getAccountId().equalsIgnoreCase(accountId))
                        {
                            errorMessage = "关键字已存在！";
                            break;
                        }
                    }
                }
                if (StringUtils.isNotEmpty(errorMessage))
                {
//                    throw new BusinessException(errorMessage);
                }
            }
        }else{
            if (list.size() > 0)
            {
                for (ResMessage resMessage : list)
                {
                    if (StringUtils.isNotEmpty(resMessage.getMsgName()) &&
                            resMessage.getMsgName().equalsIgnoreCase(msgName) &&
                            resMessage.getAccountId().equalsIgnoreCase(accountId) &&
                            resMessage.getId().equals(id))
                    {
                        errorMessage = "消息名称已存在！";
                        break;
                    }
                    else if (StringUtils.isNotEmpty(resMessage.getKeyWords()) &&
                            resMessage.getKeyWords().equalsIgnoreCase(keyWords) &&
                            resMessage.getAccountId().equalsIgnoreCase(accountId) &&
                            resMessage.getId().equals(id))
                    {
                        errorMessage = "关键字已存在！";
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(errorMessage))
                {
//                    throw new BusinessException(errorMessage);
                }
            }
        }
    }
}
