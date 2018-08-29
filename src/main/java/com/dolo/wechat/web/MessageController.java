package com.dolo.wechat.web;

import com.dolo.wechat.business.MessageService;
import com.dolo.wechat.business.WxApiService;
import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.common.util.CommonUtil;
import com.dolo.wechat.common.util.SignUtil;
import com.dolo.wechat.common.util.XmlUtil;
import com.dolo.wechat.entity.AccessToken;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.recmsg.BaseMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
* 描述: 微信消息处理控制器
* 作者: dolojia
* 修改日期: 2018/8/29 下午9:35
* E-mail: dolojia@gmail.com
**/
@Controller
public class MessageController extends  BaseController
{

    @Autowired
    private MessageService messageService;

    @Autowired
    private WxApiService wxApiService;

    /**
     * 方法名称:
     * 描述：微信接入主方法
     * 作者: dolojia
     * 修改日期：2018/8/29 下午9:40
     */
    @RequestMapping(value = "/wxweb", method = RequestMethod.GET)
    public void validateWeixin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "signature", required = false)
    String signature, @RequestParam(value = "timestamp", required = false)
    String timestamp, @RequestParam(value = "nonce", required = false)
    String nonce, @RequestParam(value = "echostr", required = false)
    String echostr)
    {
        try
        {
            PrintWriter out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            logger.info("SignUtil.checkSignature[token1]="+ AppConfigProperties.getToken()+"[token1]="+ AppConfigProperties.getToken2()+"[signature]="+signature+"[nonce]="+nonce);
            if (SignUtil.checkSignature( AppConfigProperties.getToken(), signature, timestamp, nonce))
            {
                out.print(echostr);
            }
            if (SignUtil.checkSignature( AppConfigProperties.getToken2(), signature, timestamp, nonce))
            {
                out.print(echostr);
            }
            out.close();
            out = null;
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("Algorithm Exception!", e);
        }
        catch (Exception e)
        {
            logger.error("Unknown Exception!", e);
        }
    }


    /**
     *
     * 功能描述：接收上行消息进行处理，然后返回下行消息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/wxweb", method = RequestMethod.POST)
    public void processMessage(HttpServletRequest request, HttpServletResponse response)
    {
        logger.info("weixin start to return message!!");
        try
        {
            request.setCharacterEncoding("UTF-8");
            String requestMessage = CommonUtil.getRequestXmlData(request);
            logger.info("**********requestMessage:" + requestMessage);
            String responseMessage = messageService.processRequestMessage(requestMessage);
            response.setCharacterEncoding("UTF-8");
            // 响应消息，有可能不需要响应
            if (StringUtils.isNotEmpty(responseMessage))
            {
                // 可能有同时返回多条消息的情况，中间用multiResMessage隔开
                if (responseMessage.indexOf("multiResMessage") > 0)
                {
                    String[] responseMessageStrs = responseMessage.split("multiResMessage");
                    logger.info("&&&&&&&&&&&&&&&&&send default text and article Message!!");
                    AccessToken accessToken = null;
                    BaseMessage baseMessage = XmlUtil.getBaseMessage(requestMessage);
                    accessToken = this.wxApiService.getAccessTokenFromBiz(baseMessage.getToUserName());
                    for (String tmpResonseMessage : responseMessageStrs)
                    {
                        // 调用客服接口发消息
                    	 logger.info("【开始调用客服接口发消息】");
                        this.wxApiService.customSend(accessToken, tmpResonseMessage);
                    }
                }
                else
                {
                    PrintWriter out = response.getWriter();
                    out.print(responseMessage);
                    out.close();
                }
            }
        }
        catch (Exception e)
        {
            logger.error("process requestMessage error!", e);
        }
    }


    /**
     *
     * 功能描述：点击图文消息，跳转到正文链接地址
     *
     * @param articleId
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/{articleId}")
    public ModelAndView jumpToArticle(@PathVariable
    long articleId, @RequestParam(value = "title", required = false)
    String title) throws UnsupportedEncodingException
    {
        title = new String(title.getBytes("iso-8859-1"), "UTF-8");
        return new ModelAndView("article_message").addObject("articleId", articleId).addObject("title", title);
    }


    /**
     *
     * 功能描述：根据图文消息的id，返回图文消息的正文
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get_article_content", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getArticleMessageContent(@RequestParam(value = "id")
    Integer id)
    {
        String content;
        try
        {
            content = this.messageService.getArticleMessageContent(id);
            return new ResultObject(content);
        }
        catch (IOException e)
        {
            logger.error("get article error!", e);
            return new ResultObject(-1, e.getMessage());
        }

    }
}
