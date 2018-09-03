package com.dolo.wechat.test.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dolo.wechat.common.util.JsonUtil;
import com.dolo.wechat.common.util.MD5Util;
import com.dolo.wechat.common.util.TransApi;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.test.BaseTests;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;

public class BaiDuTranslateTest extends BaseTests{

    private static final String APP_ID = "20180903000202341";
    private static final String SECURITY_KEY = "NmIfwrT7qmVsE_uGKods";


    @Test
    public void translateTest(){
        String responseMessage = "";

        String content = "翻译Document ation Plugins Boot strapper";
        System.out.println(AppConfigProperties.getBaiDuAppId());
        System.out.println(AppConfigProperties.getBaiDuAppKey());
        System.out.println(AppConfigProperties.getAppid());
        TransApi api = new TransApi(AppConfigProperties.getBaiDuAppId(), AppConfigProperties.getBaiDuAppKey());
        String result = api.getTransResult(content.replace("翻译",""), "auto", "auto");
        System.out.println(result);
        JSONObject jsonObject = JsonUtil.stringToJSONObject(result);
        if (StringUtils.isEmpty(jsonObject.getString("error_code"))){
            JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
            JSONObject transObject = jsonArray.getJSONObject(0);
            responseMessage = transObject.getString("dst");
        }else{
            responseMessage = "翻译失败";
        }
        System.out.println(responseMessage);
    }
}
