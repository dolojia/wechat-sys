package com.dolo.wechat.recmsg;

import com.dolo.wechat.common.util.DateUtil;
import com.dolo.wechat.common.util.Util;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;


/**
* 描述: 消息的基础字段
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:21
* E-mail: dolojia@gmail.com
**/
public class BaseMessage {
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
    
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
    
	/**
	 * 消息创建时间 （整型）
	 */
	private long CreateTime;
	
	/**
	 * 消息类型（text/image/location/link）
	 */
	private String MsgType;
	
	/**
	 * 消息id，64位整型
	 */
	private Long MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return DateUtil.convert2String(this.CreateTime*1000,null);
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

    public Long getMsgId()
    {
        return MsgId;
    }

    public void setMsgId(Long msgId)
    {
        MsgId = msgId;
    }
    
    public static void main(String[] args)
    {
        long createTime = 1348831860;
        System.out.println(DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss"));
    }
	
}