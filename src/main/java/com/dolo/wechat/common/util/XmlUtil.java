package com.dolo.wechat.common.util;


import com.alibaba.fastjson.JSONObject;
import com.dolo.wechat.business.MessageFactory;
import com.dolo.wechat.common.xml.BaseEventMessageXStream;
import com.dolo.wechat.common.xml.BaseMessageXStream;
import com.dolo.wechat.common.xml.CDATAXppDriver;
import com.dolo.wechat.entity.ArticleMessageItem;
import com.dolo.wechat.recmsg.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * xml解析工具类，运用XStream包解析，减少解析代码
 * 
 * @类名： XmlParserUtils.java
 * @描述：
 * @作者： mxyanx
 * @修改日期： 2014年7月2日
 */
public class XmlUtil
{

    /**
     * 
     * 功能描述：解析上行消息报文，返回基础消息对象
     * 
     * @param requestXmlData
     * @return
     * @throws FileNotFoundException
     */
    public static BaseMessage getBaseMessage(String requestXmlData) throws FileNotFoundException, StreamException
    {
        BaseMessageXStream baseMessageXStream = new BaseMessageXStream();
        BaseMessage baseMessage = new BaseMessage();
        InputStream input = new ByteArrayInputStream(requestXmlData.getBytes());
        baseMessageXStream.alias("xml", BaseMessage.class);
        baseMessageXStream.fromXML(input, baseMessage);
        return baseMessage;
    }


    /**
     * 
     * 功能描述：解析上行消息报文，返回事件消息对象
     * 
     * @param requestXmlData
     * @return
     */
    public static BaseEventMessage getBaseEventMessage(String requestXmlData)
    {
        BaseEventMessageXStream baseEventMessageXStream = new BaseEventMessageXStream();
        BaseEventMessage baseEventMessage = new BaseEventMessage();
        InputStream input = new ByteArrayInputStream(requestXmlData.getBytes());
        baseEventMessageXStream.alias("xml", BaseEventMessage.class);
        baseEventMessageXStream.fromXML(input, baseEventMessage);
        return baseEventMessage;
    }


    /**
     * 
     * 功能描述：根据消息类型解析成不同的对象
     * 
     * @param msgType
     * @param requestXmlData
     * @return
     */
    public static BaseMessage getMessageByType(String msgType, String requestXmlData)
    {
        XStream msgStream = new XStream(new CDATAXppDriver());
        BaseMessage baseMessage = MessageFactory.getMessageByMessageType(msgType);
        InputStream input = new ByteArrayInputStream(requestXmlData.getBytes());
        msgStream.processAnnotations(baseMessage.getClass());
        msgStream.fromXML(input, baseMessage);
        return baseMessage;
    }


    /**
     * 
     * 功能描述：根据事件类型解析成不同的事件对象
     * 
     * @param event
     * @param requestXmlData
     * @return
     */
    public static BaseEventMessage getEventMessageByType(String event, String requestXmlData)
    {
        XStream msgStream = new XStream(new CDATAXppDriver());
        BaseEventMessage baseEventMessage = MessageFactory.getEventMessageByEvent(event);
        InputStream input = new ByteArrayInputStream(requestXmlData.getBytes());
        msgStream.processAnnotations(baseEventMessage.getClass());
        msgStream.fromXML(input, baseEventMessage);
        return baseEventMessage;
    }


    /**
     * 
     * 功能描述：获取上行的text类型消息的content
     * 
     * @param requestXmlData
     * @return
     */
    public static String getTextMessageContent(String requestXmlData)
    {
        XStream msgStream = new XStream(new CDATAXppDriver());
        TextMessage textMessage = new TextMessage();
        InputStream input = new ByteArrayInputStream(requestXmlData.getBytes());
        msgStream.processAnnotations(TextMessage.class);
        msgStream.fromXML(input, textMessage);
        return textMessage.getContent();
    }


    /**
     * 
     * 功能描述：根据数据库中的content响应给用户text类型的消息
     * 
     * @param content
     * @return
     */
    public static String getResTextMessage(String content, BaseMessage baseMessage)
    {
        ResTextMessage resTextMessage = new ResTextMessage();
        resTextMessage.setToUserName(baseMessage.getFromUserName());
        resTextMessage.setFromUserName(baseMessage.getToUserName());
        resTextMessage.setCreateTime("" + System.currentTimeMillis() / 1000);
        resTextMessage.setMsgType("text");
        resTextMessage.setContent(content);
        XStream xStream = CDATAXppDriver.createXstream();
        xStream.processAnnotations(ResTextMessage.class);
        String resTextMessageStr = xStream.toXML(resTextMessage);
        return resTextMessageStr;
    }


    /**
     * 
     * 功能描述：返回消息组装成json字符串
     * 
     * @param content
     * @param baseMessage
     * @return
     */
    public static String getResTextMessageJsonStr(String content, BaseMessage baseMessage)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", baseMessage.getFromUserName());
        map.put("msgtype", "text");
        Map<String, Object> contentMap = new TreeMap<String, Object>();
        contentMap.put("content", content);
        map.put("text", contentMap);
        JSONObject json = JSONObject.parseObject(map.toString());
        return json.toString();
    }


    /**
     * 
     * 功能描述：根据数据库中的图文item项，生成响应给用户的多图文消息
     * 
     * @param articleMessageItems
     * @param baseMessage
     * @return
     */
    public static String getResArticleMessage(List<ArticleMessageItem> articleMessageItems, BaseMessage baseMessage, String wxwebUrl)
    {
        ResArticleMessage resArticleMessage = new ResArticleMessage();
        resArticleMessage.setToUserName(baseMessage.getFromUserName());
        resArticleMessage.setFromUserName(baseMessage.getToUserName());
        resArticleMessage.setCreateTime("" + System.currentTimeMillis() / 1000);
        resArticleMessage.setMsgType("news");
        resArticleMessage.setArticleCount(articleMessageItems.size());
        List<ArticleItem> articles = new ArrayList<ArticleItem>();
        ArticleItem item;
        for (int i = 0; i < articleMessageItems.size(); i++)
        {
            item = new ArticleItem();
            item.setTitle(articleMessageItems.get(i).getTitle());
            item.setDescription(articleMessageItems.get(i).getDescription());
            item.setPicUrl(articleMessageItems.get(i).getPicUrl());
            String url = articleMessageItems.get(i).getUrl();
            if (StringUtils.isEmpty(url))
            {
                item.setUrl(wxwebUrl + "/" + articleMessageItems.get(i).getId() + ".xhtml?title="+articleMessageItems.get(i).getTitle());
            }
            else
            {
                item.setUrl(url);
            }
            articles.add(item);
        }
        resArticleMessage.setArticles(articles);
        XStream xStream = CDATAXppDriver.createXstream();
        xStream.processAnnotations(ResArticleMessage.class);
        String resArticleMessageStr = xStream.toXML(resArticleMessage);
        return resArticleMessageStr;
    }


    /**
     * 
     * 功能描述：返回的图文消息组装成json字符串
     * 
     * @param articleMessageItems
     * @param baseMessage
     * @param wxwebUrl
     * @return
     */
    public static String getResArticleMessageJsonStr(List<ArticleMessageItem> articleMessageItems, BaseMessage baseMessage, String wxwebUrl)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("touser", baseMessage.getFromUserName());
        resultMap.put("msgtype", "news");
        Map<String, Object> contentMap = new HashMap<String, Object>();
        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
        Map<String, Object> tmpMap;
        for (int i = 0; i < articleMessageItems.size(); i++)
        {
            tmpMap = new HashMap<String, Object>();
            tmpMap.put("title", articleMessageItems.get(i).getTitle());
            tmpMap.put("description", articleMessageItems.get(i).getDescription());
            String url = articleMessageItems.get(i).getUrl();
            if (StringUtils.isEmpty(url))
            {
                tmpMap.put("url", wxwebUrl + "/" + articleMessageItems.get(i).getId() + ".xhtml?title="+articleMessageItems.get(i).getTitle());
            }
            else
            {
                tmpMap.put("url", url);
            }
            tmpMap.put("picurl", articleMessageItems.get(i).getPicUrl());
            itemList.add(tmpMap);
        }
        contentMap.put("articles", itemList);
        resultMap.put("news", contentMap);
        JSONObject json = JSONObject.parseObject(resultMap.toString());
        return json.toString();
    }


    public static void main(String[] args)
    {
        System.out.println(StringUtils.replace("/wxadmin/upload/Penguins.jpg", "wxadmin", "wxpic"));
    }

}
