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
@TableName("annual_rate")
public class AnnualRate extends Model<AnnualRate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "annual_rate_id", type = IdType.AUTO)
    private String annualRateId;
    @TableField("annual_rate_year")
    private String annualRateYear;
    @TableField("year_up_down")
    private String yearUpDown;
    @TableField("annual_rate")
    private String annualRate;
    @TableField("create_time")
    private Date createTime;


    public String getAnnualRateId() {
        return annualRateId;
    }

    public void setAnnualRateId(String annualRateId) {
        this.annualRateId = annualRateId;
    }

    public String getAnnualRateYear() {
        return annualRateYear;
    }

    public void setAnnualRateYear(String annualRateYear) {
        this.annualRateYear = annualRateYear;
    }

    public String getYearUpDown() {
        return yearUpDown;
    }

    public void setYearUpDown(String yearUpDown) {
        this.yearUpDown = yearUpDown;
    }

    public String getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(String annualRate) {
        this.annualRate = annualRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.annualRateId;
    }

    @Override
    public String toString() {
        return "AnnualRate{" +
        "annualRateId=" + annualRateId +
        ", annualRateYear=" + annualRateYear +
        ", yearUpDown=" + yearUpDown +
        ", annualRate=" + annualRate +
        ", createTime=" + createTime +
        "}";
    }
}
