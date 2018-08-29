package com.dolo.wechat.entity;

import java.util.Date;
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
@TableName("wx_user")
public class WxUser extends Model<WxUser> {

    private static final long serialVersionUID = 1L;

    private String openid;
    private String subscribe;
    private String nickname;
    private String sex;
    private String city;
    private String province;
    private String country;
    private String language;
    private String headimgurl;
    @TableField("subscribe_time")
    private Date subscribeTime;
    @TableField("unsubscribe_time")
    private Date unsubscribeTime;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private String unionid;
    @TableField("id_card_number")
    private String idCardNumber;
    @TableField("subscribe_flag")
    private String subscribeFlag;
    @TableField("account_id")
    private String accountId;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Date getUnsubscribeTime() {
        return unsubscribeTime;
    }

    public void setUnsubscribeTime(Date unsubscribeTime) {
        this.unsubscribeTime = unsubscribeTime;
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

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getSubscribeFlag() {
        return subscribeFlag;
    }

    public void setSubscribeFlag(String subscribeFlag) {
        this.subscribeFlag = subscribeFlag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    protected Serializable pkVal() {
        return this.openid;
    }

    @Override
    public String toString() {
        return "WxUser{" +
        "openid=" + openid +
        ", subscribe=" + subscribe +
        ", nickname=" + nickname +
        ", sex=" + sex +
        ", city=" + city +
        ", province=" + province +
        ", country=" + country +
        ", language=" + language +
        ", headimgurl=" + headimgurl +
        ", subscribeTime=" + subscribeTime +
        ", unsubscribeTime=" + unsubscribeTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", unionid=" + unionid +
        ", idCardNumber=" + idCardNumber +
        ", subscribeFlag=" + subscribeFlag +
        ", accountId=" + accountId +
        "}";
    }
}
