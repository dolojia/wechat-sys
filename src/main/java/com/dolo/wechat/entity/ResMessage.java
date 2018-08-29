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
@TableName("res_message")
public class ResMessage extends Model<ResMessage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("msg_name")
    private String msgName;
    @TableField("msg_type")
    private String msgType;
    @TableField("res_type")
    private String resType;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private String creator;
    private String properties;
    @TableField("key_words")
    private String keyWords;
    private String status;
    @TableField("account_id")
    private String accountId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResMessage{" +
        "id=" + id +
        ", msgName=" + msgName +
        ", msgType=" + msgType +
        ", resType=" + resType +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", creator=" + creator +
        ", properties=" + properties +
        ", keyWords=" + keyWords +
        ", status=" + status +
        ", accountId=" + accountId +
        "}";
    }
}
