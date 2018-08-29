package com.dolo.wechat.business;

import com.dolo.wechat.recmsg.*;

/**
* 描述: 文本消息
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:27
* E-mail: dolojia@gmail.com
**/
public class MessageFactory
{
    /**
     * 
     * 功能描述：根据消息类型返回不同的消息对象
     * @param msgType
     * @return
     */
    public static BaseMessage getMessageByMessageType(String msgType)
    {
        if (msgType.equalsIgnoreCase("text"))
        {
            return new TextMessage();
        }else if(msgType.equalsIgnoreCase("image")){
            return new ImageMessage();
        }else if(msgType.equalsIgnoreCase("location")){
            return new LocationMessage();
        }
        else
            return null;
    }
    
    
    /**
     * 
     * 功能描述：根据不同的事件返回不同的事件消息对象
     * @param event
     * @return
     */
    public static BaseEventMessage getEventMessageByEvent(String event){
        if (event.equalsIgnoreCase("subscribe") || event.equalsIgnoreCase("unsubscribe"))
        {
            return new SubscribeEventMessage();
        }else if(event.equalsIgnoreCase("firstSubscribe") || event.equalsIgnoreCase("SCAN")){
            return new ScanEventMessage();
        }else if(event.equalsIgnoreCase("LOCATION")){
            return new LocationEventMessage();
        }else if(event.equalsIgnoreCase("VIEW") || event.equalsIgnoreCase("CLICK")){
            return new MenuEventMessage();
        }
        else
            return null;
    }
}
