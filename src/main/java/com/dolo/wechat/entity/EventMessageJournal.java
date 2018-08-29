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
@TableName("event_message_journal")
public class EventMessageJournal extends Model<EventMessageJournal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("to_user_name")
    private String toUserName;
    @TableField("from_user_name")
    private String fromUserName;
    @TableField("create_time")
    private Date createTime;
    private String event;
    @TableField("event_key")
    private String eventKey;
    private String ticket;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
        return "EventMessageJournal{" +
        "id=" + id +
        ", toUserName=" + toUserName +
        ", fromUserName=" + fromUserName +
        ", createTime=" + createTime +
        ", event=" + event +
        ", eventKey=" + eventKey +
        ", ticket=" + ticket +
        ", properties=" + properties +
        ", sendFlag=" + sendFlag +
        "}";
    }
}
