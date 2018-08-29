package com.dolo.wechat.entity;

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
@TableName("msg_info")
public class MsgInfo extends Model<MsgInfo> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;
    @TableField("MAKEDATE")
    private Date makedate;
    @TableField("MAKETIME")
    private String maketime;
    @TableField("MSGTYPE")
    private String msgtype;
    @TableField("MSGDIRECTION")
    private String msgdirection;
    @TableField("USERID")
    private String userid;
    @TableField("USERNAME")
    private String username;
    @TableField("MSGTILE")
    private String msgtile;
    @TableField("CONTENT")
    private Object content;
    @TableField("CONTENTPRE")
    private String contentpre;
    @TableField("SYSTEMCODE")
    private String systemcode;
    @TableField("SERVICECODE")
    private String servicecode;
    @TableField("DATATYPE")
    private String datatype;
    @TableField("SENDWAY")
    private String sendway;
    @TableField("FIXEDDATE")
    private Date fixeddate;
    @TableField("FIXEDTIME")
    private String fixedtime;
    @TableField("UNITCODE")
    private String unitcode;
    @TableField("OPERCODE")
    private String opercode;
    @TableField("SYSOPERDATE")
    private Date sysoperdate;
    @TableField("SYSOPERTIME")
    private String sysopertime;
    @TableField("TEMP1")
    private String temp1;
    @TableField("TEMP2")
    private String temp2;
    @TableField("TEMP3")
    private String temp3;
    @TableField("CUSTOMERID")
    private String customerid;
    @TableField("REPORTDATE")
    private Date reportdate;
    @TableField("REPORTTIME")
    private String reporttime;
    @TableField("DEALORDER")
    private String dealorder;
    @TableField("LASTSENDDATE")
    private Date lastsenddate;
    @TableField("LASTSENDTIME")
    private String lastsendtime;
    @TableField("SENDTIMES")
    private String sendtimes;
    @TableField("MSGSTATE")
    private String msgstate;
    @TableField("FILTERTYPE")
    private String filtertype;
    @TableField("TEMP4")
    private String temp4;
    @TableField("TEMP5")
    private String temp5;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgdirection() {
        return msgdirection;
    }

    public void setMsgdirection(String msgdirection) {
        this.msgdirection = msgdirection;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsgtile() {
        return msgtile;
    }

    public void setMsgtile(String msgtile) {
        this.msgtile = msgtile;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getContentpre() {
        return contentpre;
    }

    public void setContentpre(String contentpre) {
        this.contentpre = contentpre;
    }

    public String getSystemcode() {
        return systemcode;
    }

    public void setSystemcode(String systemcode) {
        this.systemcode = systemcode;
    }

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getSendway() {
        return sendway;
    }

    public void setSendway(String sendway) {
        this.sendway = sendway;
    }

    public Date getFixeddate() {
        return fixeddate;
    }

    public void setFixeddate(Date fixeddate) {
        this.fixeddate = fixeddate;
    }

    public String getFixedtime() {
        return fixedtime;
    }

    public void setFixedtime(String fixedtime) {
        this.fixedtime = fixedtime;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getOpercode() {
        return opercode;
    }

    public void setOpercode(String opercode) {
        this.opercode = opercode;
    }

    public Date getSysoperdate() {
        return sysoperdate;
    }

    public void setSysoperdate(Date sysoperdate) {
        this.sysoperdate = sysoperdate;
    }

    public String getSysopertime() {
        return sysopertime;
    }

    public void setSysopertime(String sysopertime) {
        this.sysopertime = sysopertime;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getDealorder() {
        return dealorder;
    }

    public void setDealorder(String dealorder) {
        this.dealorder = dealorder;
    }

    public Date getLastsenddate() {
        return lastsenddate;
    }

    public void setLastsenddate(Date lastsenddate) {
        this.lastsenddate = lastsenddate;
    }

    public String getLastsendtime() {
        return lastsendtime;
    }

    public void setLastsendtime(String lastsendtime) {
        this.lastsendtime = lastsendtime;
    }

    public String getSendtimes() {
        return sendtimes;
    }

    public void setSendtimes(String sendtimes) {
        this.sendtimes = sendtimes;
    }

    public String getMsgstate() {
        return msgstate;
    }

    public void setMsgstate(String msgstate) {
        this.msgstate = msgstate;
    }

    public String getFiltertype() {
        return filtertype;
    }

    public void setFiltertype(String filtertype) {
        this.filtertype = filtertype;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MsgInfo{" +
        "id=" + id +
        ", makedate=" + makedate +
        ", maketime=" + maketime +
        ", msgtype=" + msgtype +
        ", msgdirection=" + msgdirection +
        ", userid=" + userid +
        ", username=" + username +
        ", msgtile=" + msgtile +
        ", content=" + content +
        ", contentpre=" + contentpre +
        ", systemcode=" + systemcode +
        ", servicecode=" + servicecode +
        ", datatype=" + datatype +
        ", sendway=" + sendway +
        ", fixeddate=" + fixeddate +
        ", fixedtime=" + fixedtime +
        ", unitcode=" + unitcode +
        ", opercode=" + opercode +
        ", sysoperdate=" + sysoperdate +
        ", sysopertime=" + sysopertime +
        ", temp1=" + temp1 +
        ", temp2=" + temp2 +
        ", temp3=" + temp3 +
        ", customerid=" + customerid +
        ", reportdate=" + reportdate +
        ", reporttime=" + reporttime +
        ", dealorder=" + dealorder +
        ", lastsenddate=" + lastsenddate +
        ", lastsendtime=" + lastsendtime +
        ", sendtimes=" + sendtimes +
        ", msgstate=" + msgstate +
        ", filtertype=" + filtertype +
        ", temp4=" + temp4 +
        ", temp5=" + temp5 +
        "}";
    }
}
