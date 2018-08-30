package com.dolo.wechat.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 描述: josn工具类
* 作者: dolojia
* 修改日期: 2018/8/30 17:32
* E-mail: dolojia@gmail.com
**/
public class JsonUtil {

    /**
     * 方法名称:
     * 描述：Object转换为tring
     * 作者: dolojia
     * 修改日期：2018/8/30
     * @param
     * @return
     */
    public static String toJSONString(Object object){
        if(ObjectUtils.isEmpty(object)){
            return "";
        }
        return JSONObject.toJSONString(object);
    }

    /**
     * 方法名称:
     * 描述：JSONObject转换为Map
     * 作者: dolojia
     * 修改日期：2018/8/30
     * @param
     * @return
     */
    public static Map<String, Object> jsonObjectToMap(JSONObject jsonObject){
        Map<String, Object> map = null;
        if(jsonObject == null || jsonObject.isEmpty()){
            map = new HashMap<>();
        }
        return JSONObject.toJavaObject(jsonObject, Map.class);
    }

    /**
     * 方法名称:
     * 描述：Map转换为JSONObject
     * 作者: dolojia
     * 修改日期：2018/8/30
     * @param
     * @return
     */
    public static JSONObject mapToJSONObject(Map<String, Object> map){
        if(ObjectUtils.isEmpty(map)){
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(map));
    }

    /**
     * 方法名称:
     * 描述：String转换为JSONObject
     * 作者: dolojia
     * 修改日期：2018/8/30
     * @param
     * @return
     */
    public static JSONObject stringToJSONObject(String jsonStr){
        if(StringUtils.isEmpty(jsonStr)){
            return null;
        }
        return JSONObject.parseObject(jsonStr);
    }
}
