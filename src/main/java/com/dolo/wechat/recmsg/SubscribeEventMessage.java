package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 关注/取消关注事件
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:24
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class SubscribeEventMessage extends BaseEventMessage
{
    private  String   EventKey;

    public String getEventKey()
    {
        return EventKey;
    }

    public void setEventKey(String eventKey)
    {
        EventKey = eventKey;
    }
}
