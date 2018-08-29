//package com.dolo.wechat.web;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.cignacmb.wxweb.utils.WeixinClient;
//
//
//@Controller
//public class PushWxMenuController {
//	private static final Logger log = Logger.getLogger(PushWxMenuController.class);
//
//    @Value("${appid}")
//    private String appid;
//
//    @Value("${appid2}")
//    private String appid2;
//
//    @Value("${secretKey}")
//    private String secretKey;
//
//    @Value("${secretKey2}")
//    private String secretKey2;
//
//    @Value("${token}")
//    private String token;
//
//    @Value("${token2}")
//    private String token2;
//
//    @Value("${accountId}")
//    private String accountId;
//
//    @Value("${accountId2}")
//    private String accountId2;
//
//	/**
//	 * 菜单推送
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/pushWxMenu.json", method = RequestMethod.POST)
//	public ModelAndView menuTree(HttpServletRequest request,HttpServletResponse response) {
//
//		String menus=request.getParameter("menuJson");
//		String accountId = request.getParameter("accountId");
//		String accessToken=request.getParameter("accessToken");
//		WeixinClient client = null;
//		if(this.accountId.equalsIgnoreCase(accountId)){
//			client = new WeixinClient(appid, secretKey, token);
//		}else if(accountId2.equalsIgnoreCase(accountId)){
//			client = new WeixinClient(appid2, secretKey2, token2);
//		}
//		 Map<String , Object> params  = new HashMap<String, Object>();
//    	 params.put("access_token", accessToken);
//    	 System.out.println(accessToken);
//		boolean flag = client.createMenus(params, menus);
//		String str = String.valueOf(flag);
//		  try {
//			response.getWriter().write(str);
//			response.getWriter().flush();
//		} catch (IOException e) {
//			log.error("PUSH WXMENU ERROR=["+e+"]");
//		}
//		return  null;
//	}
//
//}
