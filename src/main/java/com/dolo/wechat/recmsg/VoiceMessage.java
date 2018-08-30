package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 语音消息
* 作者: dolojia
* 修改日期: 2018/8/30 16:04
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class VoiceMessage extends BaseMessage {
	
    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
	private String MediaId;

	/**
	 * 语音格式，如amr，speex等
	 */
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}