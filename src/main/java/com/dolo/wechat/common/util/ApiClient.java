package com.dolo.wechat.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.dolo.wechat.common.constants.APIConstants;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient {
    protected Logger logger = LogManager.getLogger(this.getClass());

    private String responseStr;


    public String getResponseStr() {
        return responseStr;
    }


    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }


    public boolean invokeApi(String urlStr, Map<String, Object> params, String content) throws IOException {
        String paramsStr = "";
        boolean flag = false;
        for (String key : params.keySet()) {
            if (flag) {
                paramsStr = paramsStr + "&";
            }
            paramsStr += key + "=" + params.get(key);
            flag = true;
        }
        if (StringUtils.isNotEmpty(paramsStr)) {
            urlStr += "?" + paramsStr;
        }
        URL url = new URL(urlStr);

        URLConnection conn = url.openConnection();
        if (StringUtils.isNotEmpty(content)) {
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), APIConstants.CHARSET);
            out.write(content);
            out.flush();
            out.close();
        }

        InputStream in = conn.getInputStream();
        BufferedReader bin = new BufferedReader(new InputStreamReader(in, APIConstants.CHARSET));
        String rs = null;
        while ((rs = bin.readLine()) != null) {
            this.responseStr = rs;
        }
        bin.close();
        in.close();
        return true;
    }


    public String invorkApiForPost(String postUrl, String content) throws IOException {
        InputStream inputStream;
        URL url = new URL(postUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        OutputStreamWriter outPost = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
        if (StringUtils.isNotEmpty(content)) {
            outPost.write(content);
        }
        outPost.flush();
        outPost.close();

        //服务器返回
        String responseStr = "";
        String totalResponseStr = "";
        inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((responseStr = reader.readLine()) != null) {
            totalResponseStr += responseStr + "\n";
        }
        return totalResponseStr;
    }

}
