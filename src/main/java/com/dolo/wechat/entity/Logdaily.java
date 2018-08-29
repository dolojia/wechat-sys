package com.dolo.wechat.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public class Logdaily extends Model<Logdaily> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private String recordId;
    @TableField("operator_name")
    private String operatorName;
    @TableField("record_type")
    private String recordType;
    private String description;
    @TableField("create_time")
    private Date createTime;
    @TableField("expend_time")
    private String expendTime;


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExpendTime() {
        return expendTime;
    }

    public void setExpendTime(String expendTime) {
        this.expendTime = expendTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }

    @Override
    public String toString() {
        return "Logdaily{" +
        "recordId=" + recordId +
        ", operatorName=" + operatorName +
        ", recordType=" + recordType +
        ", description=" + description +
        ", createTime=" + createTime +
        ", expendTime=" + expendTime +
        "}";
    }
}
