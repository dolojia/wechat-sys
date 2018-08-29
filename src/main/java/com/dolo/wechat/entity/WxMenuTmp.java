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
@TableName("wx_menu_tmp")
public class WxMenuTmp extends Model<WxMenuTmp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("menu_id")
    private String menuId;
    @TableField("parent_id")
    private String parentId;
    private String name;
    @TableField("menu_type")
    private String menuType;
    @TableField("menu_order")
    private String menuOrder;
    private String role;
    private String properties;
    private String flag;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private String status;
    @TableField("account_id")
    private String accountId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
        return "WxMenuTmp{" +
        "id=" + id +
        ", menuId=" + menuId +
        ", parentId=" + parentId +
        ", name=" + name +
        ", menuType=" + menuType +
        ", menuOrder=" + menuOrder +
        ", role=" + role +
        ", properties=" + properties +
        ", flag=" + flag +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", accountId=" + accountId +
        "}";
    }
}
