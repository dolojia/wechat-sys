package com.dolo.wechat.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dolo.wechat.common.constants.APIConstants;
import com.dolo.wechat.common.util.WeixinClient;
import com.dolo.wechat.entity.AccessToken;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.service.IAccessTokenService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述: 微信api服务类
 * 作者: dolojia
 * 修改日期: 2018/8/29 16:56
 * E-mail: dolojia@gmail.com
 **/
@Service
public class WxApiService {

    protected final Log logger = LogFactory.getLog(getClass());


    @Autowired
    private IAccessTokenService accessTokenService;

    /**
     * 功能描述：调用api返回用户信息
     *
     * @param userInfoParam
     * @return
     */
    public String getUserInfoFromApi(String userInfoParam) {
        WeixinClient weixinClient = new WeixinClient();
        if (StringUtils.isEmpty(userInfoParam) || userInfoParam.indexOf(",") < 0) {
            return "";
        }
        String[] params = userInfoParam.split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", params[0]);
        map.put("openId", params[1]);
        map.put("lang", "zh_CN");
        String userInfoJson = "";
        try {
            userInfoJson = weixinClient.getUserInfo(map);
        } catch (IOException e) {
            logger.error("getUserInfo api invork error!", e);
            return "";
        }
        return userInfoJson;
    }


    /**
     * 功能描述：调用api返回关注者列表所有OpenId
     *
     * @param userInfoParam
     * @return
     */
    public List getOpenIdList(String access_token) {
        WeixinClient weixinClient = new WeixinClient();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", access_token);
        int total = 0;
        int count = 0;
        String openids = "";
        String next_openid = "";
        List<String> openIdList = new ArrayList<String>();
        do {
            params.put("next_openid", next_openid);
            JSONObject openIdData;
            try {
                openIdData = weixinClient.getUserOpenIdList(params);
                JSONObject data = openIdData.getJSONObject("data");
                openids = data.getString("openid").replace("\"", "").replace("[", "").replace("]", "");
                String[] openIds = openids.split(",");
                total = Integer.parseInt(openIdData.getString("total"));
                count = Integer.parseInt(openIdData.getString("count"));
                next_openid = openIdData.getString("next_openid");
                for (String openId : openIds) {
                    openIdList.add(openId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (total > 10000 && count == 10000);
        return openIdList;
    }


    /**
     * 功能描述：调用微信api返回accessToken
     *
     * @param appid
     * @param secretKey
     * @return
     */
    public String getAccessTokenFromApi(String appid, String secretKey) {
        WeixinClient weixinClient = new WeixinClient(appid, secretKey, null);
        JSONObject jsonObj = weixinClient.getAccessToken();
        if (jsonObj != null) {
            String accessToken = jsonObj.getString("access_token");
            if (StringUtils.isNotEmpty(accessToken)) {
                return accessToken;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 功能描述：从db获取accessToken返回给外部
     *
     * @return
     * @throws IOException
     */
    public AccessToken getAccessTokenFromBiz(String accountId) throws IOException {
        try {
            accessTokenService.getAccessTokenByAccountId(accountId);
            AccessToken accessToken = accessTokenService.getAccessTokenByAccountId(accountId);
            logger.info("~~~~~~从db获取accessToken返回给外部】" + JSON.toJSONString(accessToken));
            if (ObjectUtils.isEmpty(accessToken)) {
                return null;
            }
            return accessToken;
        } catch (Exception e) {
            logger.error("从db获取accessToken异常", e);
        }
        return null;
    }


    /**
     * 功能描述：调用客户消息接口
     *
     * @param messageJsonStr
     * @throws IOException
     */
    public void customSend(AccessToken accessToken, String messageJsonStr) throws IOException {
        WeixinClient weixinClient = null;
        String accountId = accessToken.getAccountId().replace("\n", "");
        if (AppConfigProperties.getAccountId().equals(accountId)) {
            weixinClient = new WeixinClient(AppConfigProperties.getAppid(), AppConfigProperties.getSecretKey(), AppConfigProperties.getToken());
            logger.info("~~~~~~~custom send params:appid=[" + AppConfigProperties.getAppid() + "],secretKey=[" + AppConfigProperties.getSecretKey() + "],token=[" + AppConfigProperties.getToken() + "],access_token=[" + accessToken.getAccessToken() + "]");
        } else if (AppConfigProperties.getAccountId2().equals(accountId)) {
            weixinClient = new WeixinClient(AppConfigProperties.getAppid2(), AppConfigProperties.getSecretKey2(), AppConfigProperties.getToken2());
            logger.info("~~~~~~~custom send params:appid2=[" + AppConfigProperties.getAppid2() + "],secretKey2=[" + AppConfigProperties.getSecretKey2() + "],token2=[" + AppConfigProperties.getToken2() + "],access_token=[" + accessToken.getAccessToken() + "]");
        }

        String sendUrl = APIConstants.CUSTOM_SEND_URL + "?access_token=" + accessToken.getAccessToken();

        weixinClient.customSend(sendUrl, messageJsonStr);
    }

    public String getOpenId(String code, String accountFlag) throws IOException {
        WeixinClient weixinClient;
        if (StringUtils.isEmpty(accountFlag) || accountFlag.equalsIgnoreCase("CS")) {
            weixinClient = new WeixinClient(AppConfigProperties.getAppid(), AppConfigProperties.getSecretKey(), AppConfigProperties.getToken());
        } else {
            weixinClient = new WeixinClient(AppConfigProperties.getAppid2(), AppConfigProperties.getSecretKey2(), AppConfigProperties.getToken2());
        }
        String openId = weixinClient.getOpenId(code);
        return openId;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "{\"access_token\":\"OezXcEiiBSKSxW0eoylIeG_AGAvA2BDXe7hOMth91f4wZ_F1iCqErbwV9EHm8jO0qBp_Dydq_r6P2vQgOcFBoy-Do7rfrwcweFqWpDqb0pAZRFN92PKe7fTZygX-3HolgIL9lU89y_7UvnlP4xu9Rw\",\"expires_in\":7200,\"refresh_token\":\"OezXcEiiBSKSxW0eoylIeG_AGAvA2BDXe7hOMth91f4wZ_F1iCqErbwV9EHm8jO0HR8cXmIITiA_psNCkYMNA3DAR6RnVYIHXpQh5FEVzpw_dG_AwEo0-EbiQHfEvN2-RxKjz_6gG6Zipx86mq9IxQ\",\"openid\":\"oKccnuHG9MaqpYxRDJmJelRe5KCE\",\"scope\":\"snsapi_base\"}";
        JSONObject jsonObject = JSONObject.parseObject(s);
        String openId = jsonObject.getString("openid");
        System.out.println("openId:" + openId);
        System.out.println(URLEncoder.encode("http://www.qq.com?", "UTF-8"));
    }

}
