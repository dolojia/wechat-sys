package com.dolo.wechat.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述: Request工具类
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:45
 * E-mail: dolojia@gmail.com
 **/
public class RequestUtils {
    /**
     * 功能描述：从Request对象中获取请求的数据
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestXmlData(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(
                new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String recievestr = sb.toString();
        return recievestr;
    }

    public static String getRealIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (!StringUtils.isEmpty(ip)) {
            // 返回多个IP时取第一个
            ip = ip.split(",")[0];
        }

        return ip;
    }
}
