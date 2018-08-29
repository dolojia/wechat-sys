package com.dolo.wechat.common.util;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字符串toString基类
 */
public class ToStringBase implements Serializable {

    /**
     * 序列号ID
     */
    private static final long serialVersionUID = 1704932592395633947L;

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
