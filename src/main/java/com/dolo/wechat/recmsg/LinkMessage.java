package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 链接消息
* 作者: dolojia
* 修改日期: 2018/8/30 16:03
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class LinkMessage extends BaseMessage {
	
    /**
     * 消息标题
     */
	private String Title;

	/**
	 * 消息描述
	 */
	private String Description;
	
	/**
	 * 消息链接
	 */
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}