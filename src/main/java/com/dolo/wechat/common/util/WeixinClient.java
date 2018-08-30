package com.dolo.wechat.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dolo.wechat.common.constants.APIConstants;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeixinClient {

    protected Logger logger = LogManager.getLogger(this.getClass());

    private ApiClient apiClient = new ApiClient();

    private String appid;

    private String secretKey;

    private String token;

    public WeixinClient() {

    }


    public WeixinClient(String appid, String secretKey, String token) {
        this.appid = appid;
        this.secretKey = secretKey;
        this.token = token;
    }


    public JSONObject getAccessToken() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "client_credential");
        params.put("appid", this.appid);
        params.put("secret", this.secretKey);
        JSONObject jsonObj = this.sendHttpRequest(APIConstants.GET_ACCESSTOKEN_URL, params);
        return jsonObj;
    }


    private JSONObject sendHttpRequest(String url, Map<String, Object> params) {
        try {
            boolean success = this.apiClient.invokeApi(url, params, "");
            if (success) {
                String reponseStr = this.apiClient.getResponseStr();
                logger.info("&&&&&&&&&&&&&&&sendHttpRequest reponseStr:" + reponseStr);
                JSONObject jsonObj = JSONObject.parseObject(reponseStr);
                if (JsonUtil.isBlank(jsonObj)) {
                    return null;
                }
                int errorCode = jsonObj.getIntValue("errcode");
                if (errorCode != 0) {
                    String errorMsg = jsonObj.getString("errmsg");
                    logger.error("request url=[" + url + "],errorCode=[" + errorCode + "],errorMsg=[" + errorMsg + "]");
                    return null;
                }
                return jsonObj;
            }
        } catch (IOException e) {
            logger.error("IO Exception！request uri=[" + url + "]", e);
            return null;
        } catch (Exception e) {
            logger.error("unknown Exception！request url=[" + url + "]", e);
            return null;
        }
        return null;
    }


    public boolean createMenus(Map<String, Object> params, String json) {
        boolean flag = false;
        try {
            flag = this.apiClient.invokeApi(APIConstants.POST_MENU_URL, params, json);
            if (flag) {
                String reponseStr = this.apiClient.getResponseStr();
                JSONObject jsonObj = JSONObject.parseObject(reponseStr);
                if (JsonUtil.isBlank(jsonObj)) {
                    return false;
                }
                int errorCode = (Integer) jsonObj.getInteger("errcode");
                if (errorCode != 0) {
                    String errorMsg = jsonObj.getString("errmsg");
                    logger.error("request url=[" + APIConstants.POST_MENU_URL + "],errorCode=[" + errorCode + "],errorMsg=[" + errorMsg + "]");
                    return false;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("createMenus fail", e);
        }
        return flag;
    }


    /**
     * 功能描述：调微信接口获取openId
     *
     * @param code
     * @return
     */
    public String getOpenId(String code) {
        String openId = "";
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("appid", appid);
            params.put("secret", secretKey);
            params.put("code", code);
            params.put("grant_type", "authorization_code");
            String json = "";
            boolean flag = this.apiClient.invokeApi(APIConstants.GET_OPENID_URL, params, json);
            if (flag) {
                String reponseStr = this.apiClient.getResponseStr();
                logger.info("getOpenId reponseStr:" + reponseStr);
                JSONObject jsonObject = JSONObject.parseObject(reponseStr);
                openId = jsonObject.getString("openid");

            }
        } catch (IOException e) {
            logger.error("getOpenId fail", e);
        }
        return openId;
    }


    /**
     * 功能描述：返回用户信息
     *
     * @param params
     * @return
     * @throws IOException
     */
    public String getUserInfo(Map<String, Object> params) throws IOException {
        boolean flag = this.apiClient.invokeApi(APIConstants.GET_USERINFO_URL, params, null);
        if (flag) {
            String reponseStr = this.apiClient.getResponseStr();
            JSONObject jsonObj = JSONObject.parseObject(reponseStr);
            if (JsonUtil.isBlank(jsonObj)) {
                return null;
            }
            int errorCode = (Integer) jsonObj.getInteger("errcode");
            if (errorCode > 0) {
                return null;
            } else {
                return reponseStr;
            }
        } else {
            return null;
        }
    }


    /**
     * 功能描述：
     ** @return
     * @throws IOException
     */
    public void customSend(String sendUrl, String messageJsonStr) throws IOException {
        String responseStr = this.apiClient.invorkApiForPost(sendUrl, messageJsonStr);
        logger.info("$$$$$responseStr:" + responseStr);
    }

    /**
     * 获得关注者列表所有openid
     *
     * @param params
     * @return
     * @throws IOException
     */
    public JSONObject getUserOpenIdList(Map<String, Object> params) throws IOException {
        boolean flag = this.apiClient.invokeApi(APIConstants.GET_OPENIDLIST_URL, params, null);
        if (flag) {
            String reponseStr = this.apiClient.getResponseStr();
            JSONObject jsonObj = JSONObject.parseObject(reponseStr);
            if (JsonUtil.isBlank(jsonObj)) {
                return null;
            }
            int errorCode = (Integer) jsonObj.getInteger("errcode");
            if (errorCode > 0) {
                return null;
            } else {
                return jsonObj;
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        WeixinClient weixinClient = new WeixinClient();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", "RTi3D6yWhxjT9m21LUi7q0A23fJfvMULpGtXXrik9SEB0SNNYITbKa-lmh5KDrF_IlHb8TfX-4_6ib3J0Xr5AfyrKVA0-y2dgj7pjpgTajA");
        int total = 0;
        int count = 0;
        String openids = "";
        String next_openid = "";
        List<String> openIdList = new ArrayList<String>();
        do {
            params.put("next_openid", next_openid);
            JSONObject openIdData = weixinClient.getUserOpenIdList(params);
            JSONObject data = openIdData.getJSONObject("data");
            openids = data.getString("openid").replace("\"", "").replace("[", "").replace("]", "");
            String[] openIds = openids.split(",");
            total = Integer.parseInt(openIdData.getString("total"));
            count = Integer.parseInt(openIdData.getString("count"));
            next_openid = openIdData.getString("next_openid");

            for (String openId : openIds) {
                openIdList.add(openId);
            }
            System.out.println(JSON.toJSON(openIdList));
        } while (total > 10000 && count == 10000);


    }

}
