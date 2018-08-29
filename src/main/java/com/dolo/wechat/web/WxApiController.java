package com.dolo.wechat.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolo.wechat.business.WxApiService;
import com.dolo.wechat.common.util.Util;
import com.dolo.wechat.entity.AccessToken;
import com.dolo.wechat.service.IAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WxApiController extends BaseController {

    @Autowired
    private WxApiService wxApiService;

    @Autowired
    private IAccessTokenService accessTokenService;

    /**
     * 功能描述：用户服务接口
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getUserInfo.xhtml", method = RequestMethod.POST)
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userInfoParam = Util.getRequestXmlData(request);
        logger.info("************userInfoParam:" + userInfoParam);
        String resopnseXmlData = wxApiService.getUserInfoFromApi(userInfoParam);
        response.setCharacterEncoding("UTF-8");
        logger.info("************userInfoXmlData:" + resopnseXmlData);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
        out.write(resopnseXmlData);
        out.flush();
        out.close();
    }


    /**
     * 功能描述：调用微信的accessToken接口
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getAccessToken.xhtml", method = RequestMethod.POST)
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pubCode = Util.getRequestXmlData(request);
        String secretKey = "";
        String accessToken = wxApiService.getAccessTokenFromApi(pubCode,secretKey);
        response.setCharacterEncoding("UTF-8");
        logger.info("%%%%%%%%%%5accessToken:" + accessToken);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
        out.write(accessToken);
        out.flush();
        out.close();
    }


    /**
     * 功能描述：对外提供accessToken服务
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getAccessToken.xhtml", method = RequestMethod.GET)
    public void getAccessToken2Client(@RequestParam(value = "accountId", required = false)
                                              String accountId, HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            AccessToken accessToken = accessTokenService.getAccessTokenByAccountId(accountId);
            if (ObjectUtils.isEmpty(accessToken)) {
                out.write("获取accessToken失败");
            }
            response.setCharacterEncoding("UTF-8");
            out.write(accessToken.getAccessToken());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("获取token异常", e);
        }

    }


    /**
     * 功能描述：获取用户的openId，重定向到用户手机侧指定的url，带上openId和用户的原始参数
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getOpenId.xhtml", method = RequestMethod.GET)
    public void getOpenId(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String rUrl = request.getParameter("r_url");
        String flag = request.getParameter("flag");
        logger.info("^^^^^^^r_url:" + rUrl);
        String openId;
        try {
            openId = wxApiService.getOpenId(code, flag);
            logger.info("^^^^^^^getOpenId openId:" + openId);
            String params = "";
            Map map = request.getParameterMap();
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (!"r_url".equalsIgnoreCase(key)) {
                    String value = ((String[]) map.get(key))[0];
                    params += "&" + key + "=" + value;
                }
            }
            params = "?openId=" + openId + params;
            response.sendRedirect(rUrl + params);
        } catch (IOException e) {
            logger.error("获取openId异常", e);
        }
    }


    /**
     * 功能描述：重定向URL，先将链接扭转至微信授权页，然后将返回至微信前端页面
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
//    @RequestMapping(value = "redirectToUrl.xhtml", method = RequestMethod.GET)
//    public ModelAndView redirectToUrl(@RequestParam(value = "url")
//                                              String url, @RequestParam(value = "flag", required = false) String flag) throws UnsupportedEncodingException {
//        String wxurl = this.wxwebUrl + "/wxweb/isFan.xhtml?r_url=" + url + "&flag=" + (StringUtils.isEmpty(flag) ? "CS" : flag);
//        String processedWxUrl = URLEncoder.encode(wxurl, "UTF-8");
//        //根据不同的标识位获取不同的appid
//        String appid = this.wxApiService.getAppIdByFlag(flag);
//        String wxoauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + processedWxUrl + "&response_type=code&scope=snsapi_base#wechat_redirect";
//        logger.info("*************wxoauthUrl:" + wxoauthUrl);
//        return new ModelAndView("redirect").addObject("wxoauthUrl", wxoauthUrl);
//    }


    /**
     * 功能描述：获取关注者列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAllWxUser.xhtml", method = RequestMethod.GET)
    public void getAllWxUser(HttpServletRequest request, HttpServletResponse response) {

        try {
            String accessToken = request.getParameter("accessToken");
            //WxApiService wxApiService = new WxApiService();
            List<String> openIdList = wxApiService.getOpenIdList(accessToken);
            List<String> userInfoList = new ArrayList<String>();
            for (String openid : openIdList) {
                String userInfoParam = accessToken + "," + openid;
                String userInfo = wxApiService.getUserInfoFromApi(userInfoParam);
                userInfoList.add(userInfo);
            }
            response.setCharacterEncoding("UTF-8");

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            out.write(userInfoList.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("获取关注者列表异常", e);
        }

    }

}
