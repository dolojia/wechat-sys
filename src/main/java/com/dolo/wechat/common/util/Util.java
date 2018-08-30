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

public class Util {

    public static String getRequestXmlData(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String recievestr = sb.toString();
        return recievestr;
    }
    public static String Utf82Gbk(String utf) {
        String l_temp = Utf8ToUnicode(utf);
        l_temp = Unicode2GBK(l_temp);
        return l_temp;
    }

    public static String Utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                sb.append(myBuffer[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String Unicode2GBK(String dataStr) {
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        int li_len = dataStr.length();
        while (index < li_len) {
            if (index >= li_len - 1
                    || !"\\u".equals(dataStr.substring(index, index + 2))) {
                buffer.append(dataStr.charAt(index));
                index++;
                continue;
            }
            String charStr = "";
            charStr = dataStr.substring(index + 2, index + 6);
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(letter);
            index += 6;
        }
        return buffer.toString();
    }
}
