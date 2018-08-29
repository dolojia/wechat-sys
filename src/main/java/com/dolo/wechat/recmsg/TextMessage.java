package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 文本消息
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:24
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {

    /**
     * 文本消息内容
     */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}