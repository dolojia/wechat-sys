package com.dolo.wechat.common.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
* 描述: 扩展了XStream，针对不需要的节点直接略过
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:17
* E-mail: dolojia@gmail.com
**/
public class BaseEventMessageXStream extends XStream {
    @Override
    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return new MapperWrapper(next) {
            @Override
            public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn, String fieldName) {
                //不能识别的节点，掠过。
                if (definedIn == Object.class) {
                    return false;
                }
                //节点名称为fileName的掠过
                if (!fieldName.equalsIgnoreCase("ToUserName")
                        && !fieldName.equalsIgnoreCase("FromUserName")
                        && !fieldName.equalsIgnoreCase("CreateTime")
                        && !fieldName.equalsIgnoreCase("MsgType")
                        && !fieldName.equalsIgnoreCase("Event")
                        && !fieldName.equalsIgnoreCase("EventKey")
                        && !fieldName.equalsIgnoreCase("Ticket")
                        && !fieldName.equalsIgnoreCase("Latitude")
                        && !fieldName.equalsIgnoreCase("Longitude")
                        && !fieldName.equalsIgnoreCase("Precision")) {
                    return false;
                }
                return super.shouldSerializeMember(definedIn, fieldName);
            }
        };
    }
}
