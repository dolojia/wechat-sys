package com.dolo.wechat.recmsg;

import com.dolo.wechat.common.xml.XStreamCDATA;


/**
* 描述: 被动响应消息的基类
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:23
* E-mail: dolojia@gmail.com
**/
public class ResBaseMessage
{
    @XStreamCDATA
    private String ToUserName;
    
    @XStreamCDATA
    private String FromUserName;
    
    private String CreateTime;
    
    @XStreamCDATA
    private String MsgType;

    public String getToUserName()
    {
        return ToUserName;
    }

    public void setToUserName(String toUserName)
    {
        ToUserName = toUserName;
    }

    public String getFromUserName()
    {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        FromUserName = fromUserName;
    }

    public String getCreateTime()
    {
        return CreateTime;
    }

    public void setCreateTime(String createTime)
    {
        CreateTime = createTime;
    }

    public String getMsgType()
    {
        return MsgType;
    }

    public void setMsgType(String msgType)
    {
        MsgType = msgType;
    }
    
    
}
