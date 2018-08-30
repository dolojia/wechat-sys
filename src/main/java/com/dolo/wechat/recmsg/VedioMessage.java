package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 描述: 视频消息
 * 作者: dolojia
 * 修改日期: 2018/8/30 16:04
 * E-mail: dolojia@gmail.com
 **/
@XStreamAlias("xml")
public class VedioMessage extends BaseMessage {
    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String MediaId;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
