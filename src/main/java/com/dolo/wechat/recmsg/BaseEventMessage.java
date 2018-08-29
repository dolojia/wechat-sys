package com.dolo.wechat.recmsg;


/**
* 描述: 事件推送消息的基础类
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:21
* E-mail: dolojia@gmail.com
**/
public class BaseEventMessage
{
    private String ToUserName;
    
    private String FromUserName;
    
    private String CreateTime;
    
    private String MsgType;
    
    private String Event;

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

    public String getEvent()
    {
        return Event;
    }

    public void setEvent(String event)
    {
        Event = event;
    }
}
