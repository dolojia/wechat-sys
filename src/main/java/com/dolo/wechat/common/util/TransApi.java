package com.dolo.wechat.common.util;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    private RestTemplate restTemplate = new RestTemplate();

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        String url =  MapUtil.getUrlWithQueryString(TRANS_API_HOST,params);
        System.out.println(url);
//        String resRturn = restTemplate.getForEntity(url,String.class).getBody();
//        System.out.println(resRturn);
        String getReturn = HttpGet.get(TRANS_API_HOST, params);
        return getReturn;
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5Util.md5LowerCase(src));

        return params;
    }

}
