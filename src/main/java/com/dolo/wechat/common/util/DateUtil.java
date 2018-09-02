package com.dolo.wechat.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* 描述: 时间工具类
* 作者: dolojia
* 修改日期: 2018/8/30 17:33
* E-mail: dolojia@gmail.com
**/
public class DateUtil {

    public static String getCurrentTimeStr() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String convert2String(long time, String format) {
        if (time > 0L) {
            if (StringUtils.isBlank(format)) {
                format = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sf.format(date);
        }
        return "";
    }

    /**
     * 功能描述：字符串转换时间格式
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date getDateByString(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 方法名称:
     * 描述：时间格式转换成字符串
     * 作者: dolojia
     * 修改日期：2018/9/2 下午8:52
     */
    public static String getStringByDate(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
