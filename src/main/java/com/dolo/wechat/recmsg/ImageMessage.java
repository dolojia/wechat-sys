package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述:
* 作者: 图片消息
* 修改日期: 2018/8/29 下午10:22
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class ImageMessage extends BaseMessage {
	
    /**
     * 图片链接
     */
	private String PicUrl;
	
	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

    public String getMediaId()
    {
        return MediaId;
    }

    public void setMediaId(String mediaId)
    {
        MediaId = mediaId;
    }
	
}