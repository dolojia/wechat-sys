package com.dolo.wechat.recmsg;

import com.dolo.wechat.common.xml.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 被动响应消息的文本消息类
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:24
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class ResTextMessage extends ResBaseMessage
{
    @XStreamCDATA
    private String Content;

    public String getContent()
    {
        return Content;
    }

    public void setContent(String content)
    {
        Content = content;
    }
}
