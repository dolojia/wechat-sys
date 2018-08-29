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
@TableName("user_bind")
public class UserBind extends Model<UserBind> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("id_no")
    private String idNo;
    @TableField("phone_no")
    private String phoneNo;
    @TableField("open_id")
    private String openId;
    @TableField("latest_bind_time")
    private Date latestBindTime;
    @TableField("latest_cancle_bind_time")
    private Date latestCancleBindTime;
    @TableField("bind_flag")
    private String bindFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getLatestBindTime() {
        return latestBindTime;
    }

    public void setLatestBindTime(Date latestBindTime) {
        this.latestBindTime = latestBindTime;
    }

    public Date getLatestCancleBindTime() {
        return latestCancleBindTime;
    }

    public void setLatestCancleBindTime(Date latestCancleBindTime) {
        this.latestCancleBindTime = latestCancleBindTime;
    }

    public String getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(String bindFlag) {
        this.bindFlag = bindFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserBind{" +
        "id=" + id +
        ", idNo=" + idNo +
        ", phoneNo=" + phoneNo +
        ", openId=" + openId +
        ", latestBindTime=" + latestBindTime +
        ", latestCancleBindTime=" + latestCancleBindTime +
        ", bindFlag=" + bindFlag +
        "}";
    }
}
