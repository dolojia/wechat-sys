package com.dolo.wechat.recmsg;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
* 描述: 上报地理位置事件
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:22
* E-mail: dolojia@gmail.com
**/
@XStreamAlias("xml")
public class LocationEventMessage extends BaseEventMessage
{
    
    private String Latitude;
    
    private String Longitude;
    
    private String Precision;

    public String getLatitude()
    {
        return Latitude;
    }

    public void setLatitude(String latitude)
    {
        Latitude = latitude;
    }

    public String getLongitude()
    {
        return Longitude;
    }

    public void setLongitude(String longitude)
    {
        Longitude = longitude;
    }

    public String getPrecision()
    {
        return Precision;
    }

    public void setPrecision(String precision)
    {
        Precision = precision;
    }
}
