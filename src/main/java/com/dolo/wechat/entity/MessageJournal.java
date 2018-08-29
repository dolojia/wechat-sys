package com.dolo.wechat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@TableName("message_journal")
public class MessageJournal extends Model<MessageJournal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("to_user_name")
    private String toUserName;
    @TableField("from_user_name")
    private String fromUserName;
    @TableField("create_time")
    private Date createTime;
    @TableField("msg_type")
    private String msgType;
    @TableField("msg_id")
    private String msgId;
    private String properties;
    @TableField("send_flag")
    private String sendFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageJournal{" +
        "id=" + id +
        ", toUserName=" + toUserName +
        ", fromUserName=" + fromUserName +
        ", createTime=" + createTime +
        ", msgType=" + msgType +
        ", msgId=" + msgId +
        ", properties=" + properties +
        ", sendFlag=" + sendFlag +
        "}";
    }
}
