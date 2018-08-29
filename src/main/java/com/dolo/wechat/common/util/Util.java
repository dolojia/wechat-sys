package com.dolo.wechat.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util
{
    public static String getCurrentTimeStr(){
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String getRequestXmlData(HttpServletRequest request) throws IOException
    {
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        String recievestr = sb.toString();
        return recievestr;
    }

    public static boolean isBlank(JSONObject jsonObj) {
        return jsonObj == null || jsonObj.isEmpty();
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     * 
     * @param time
     * @param format
     * @return
     */
    public static String convert2String(long time, String format)
    {
        if (time > 0l)
        {
            if (StringUtils.isBlank(format))
            {
                format = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sf.format(date);
        }
        return "";
    }
    
    /**
     * 
     * 功能描述：字符串转换时间格式
     * 
     * @param string_DATE
     * @return
     * @throws ParseException
     */
    public static Date getDateByString(String string_DATE) throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(string_DATE);
    }
}
