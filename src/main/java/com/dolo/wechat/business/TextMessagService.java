package com.dolo.wechat.business;

import com.dolo.wechat.common.constants.ApprovedStatusEnum;
import com.dolo.wechat.common.util.LuceneUtil;
import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.manage.log.Operator;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.service.IResMessageService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 描述: 文本消息服务类
* 作者: dolojia
* 修改日期: 2018/8/30 下午11:08
* E-mail: dolojia@gmail.com
**/
@Service
public class TextMessagService
{

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IResMessageService resMessagService;


    /**
     * @param userName
     *            操作人
     * @param id
     *            消息主键
     * @param msgName
     *            消息名称
     * @param content
     *            消息内容
     * @param keyWords
     *            关键字
     * @throws IOException
     */
    @Operator(operator="增加或更新文本消息")
    public void saveTextMessage(String userName, String id, String msgName, String content, String keyWords,String resType,String accountId) throws IOException
    { // 保存到数据库
        ResMessage resMessage = new ResMessage();
        resMessage.setMsgName(msgName);
        resMessage.setMsgType("text");
        resMessage.setCreator(userName);
        resMessage.setProperties(content);
        resMessage.setStatus(ApprovedStatusEnum.UNSUBMIT.name());
        resMessage.setKeyWords(keyWords);
        resMessage.setResType(resType);
        resMessage.setAccountId(accountId);
        resMessagService.checkResMessage(id,msgName, keyWords,accountId);
        if (StringUtils.isEmpty(id))
        {
            // 新增
            resMessage.setCreateTime(new Date());
            this.resMessagService.insert(resMessage);
        }
        else
        {
            // 修改记录
            resMessage.setId(NumberUtils.toLong(id));
            resMessage.setUpdateTime(new Date());
            resMessagService.updateById(resMessage);
        }
        // 清除Lucene文件夹中的内容，然后重新生成搜索内容
        Map<String,Object> map = new HashMap<>();
        map.put("1",1);
        List<ResMessage> resMessageList = resMessagService.selectByMap(map);
        LuceneUtil.generatorLuceneIndex(resMessage.getAccountId(), AppConfigProperties.getLucenePath(),resMessageList);
    }


    /**
     * 
     * 功能描述：根据id返回文本消息
     * 
     * @param id
     * @return
     */
    public ResMessage getTextMessageById(String id)
    {
        return resMessagService.selectById(id);
    }
    
}
