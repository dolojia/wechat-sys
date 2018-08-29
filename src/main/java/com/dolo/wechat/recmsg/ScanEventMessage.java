package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 扫描参数二维码事件
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:24
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class ScanEventMessage extends BaseEventMessage
{
    
    private  String   EventKey;
    
    private  String   Ticket;

    public String getEventKey()
    {
        return EventKey;
    }

    public void setEventKey(String eventKey)
    {
        EventKey = eventKey;
    }

    public String getTicket()
    {
        return Ticket;
    }

    public void setTicket(String ticket)
    {
        Ticket = ticket;
    }
}
