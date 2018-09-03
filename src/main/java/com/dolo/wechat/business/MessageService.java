package com.dolo.wechat.business;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dolo.wechat.common.util.*;
import com.dolo.wechat.entity.*;
import com.dolo.wechat.factory.ResMessageFactory;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.recmsg.*;
import com.dolo.wechat.service.*;
import com.dolo.wechat.service.impl.WxUserServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.http2.StreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* 描述: 手机前端页面的消息处理Service类，主要用来处理接收到的用户上行消息和下发返回消息
* 作者: dolojia
* 修改日期: 2018/8/29 下午10:28
* E-mail: dolojia@gmail.com
**/
@Service
public class MessageService
{
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private LuceneService luceneService;

    @Autowired
    private IResMessageService resMessageService;
    
    @Autowired
    private IArticleMessageItemService articleMessageItemService;

    @Autowired
    private IMessageJournalService messageJournalService;

    @Autowired
    private IUserBindService userBindService;

    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private IEventMessageJournalService eventMessageJournalService;

    @Autowired
    private IAccessTokenService accessTokenService;

    @Autowired
    private WxApiService wxApiService;

    @Autowired
    private IWxLocationService wxLocationService;

    /**
     * 
     * 功能描述：处理上行消息，入库和返回
     * 
     * @param requestXmlData
     * @return
     * @throws StreamException
     * @throws IOException
     */
    public String processRequestMessage(String requestXmlData) throws StreamException, IOException, ParseException {
        if (StringUtils.isEmpty(requestXmlData))
        {
            logger.error("requestXmlData is empty!");
            return null;
        }
        BaseMessage baseMessage = XmlUtil.getBaseMessage(requestXmlData);

        String responseMessage ="";
        // 处理上行消息
        if (baseMessage.getMsgType().equalsIgnoreCase("text"))
        {
            // 调用biz工程的接口进行回消息处理，传过去的参数只是content等字符串
            String content = XmlUtil.getTextMessageContent(requestXmlData);
            logger.info("text content:" + content);
            // 上行(用户关键字)将消息入库
            this.insertMessage2DB(requestXmlData, baseMessage);
            // 根据用户发的关键字去找匹配的文本内容，到账号对应的目录下查找消息id
            String messageId = luceneService.getMessageId(content, baseMessage.getToUserName());
            responseMessage = this.getResMessageById(messageId, baseMessage);

            if (content.startsWith("翻译")){
                TransApi api = new TransApi(AppConfigProperties.getBaiDuAppId(), AppConfigProperties.getBaiDuAppKey());
                String result = api.getTransResult(content.replace("翻译",""), "auto", "auto");
                JSONObject jsonObject = JsonUtil.stringToJSONObject(result);
                if (StringUtils.isEmpty(jsonObject.getString("error_code"))){
                    JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
                    JSONObject transObject = jsonArray.getJSONObject(0);
                    responseMessage = transObject.getString("dst");
                }else{
                    responseMessage = "翻译失败";
                }
            }
            // 下行消息入库
            if (StringUtils.isEmpty(responseMessage))
            {
                // 根据账号从工厂中返回默认消息的key值
                String defaultMessageKey = ResMessageFactory.getDefaultMessageKeyByAccountId(AppConfigProperties.getAccountId(), AppConfigProperties.getAccountId2(), baseMessage);

                ResMessage defaultMessage = resMessageService.getResMessageByAccountIdAndMessageKey(defaultMessageKey,baseMessage.getToUserName());
                String defaultMessageStr = "";
                if (defaultMessage == null)
                {
                    defaultMessageStr = ResMessageFactory.getDefaultMessageByAccountId(AppConfigProperties.getAccountId(), AppConfigProperties.getAccountId2(), baseMessage);
                }
                else
                {
                    defaultMessageStr = defaultMessage.getProperties();
                }
                responseMessage = XmlUtil.getResTextMessage(defaultMessageStr, baseMessage);
                // 返回消息入库
                this.insertResTextMessage2DB(defaultMessageStr, baseMessage);
            }
        }
        else if (baseMessage.getMsgType().equalsIgnoreCase("location"))
        {
            // 用户主动上报地理位置信息入库
            this.insertMessage2DB(requestXmlData, baseMessage);
            // 接收用户上报地理位置消息
            LocationMessage locationMessage = (LocationMessage) XmlUtil.getMessageByType("location", requestXmlData);
            // 根据账号来查找最近的公司
            WxLocation wxLocation = this.getShortestLocation(locationMessage, baseMessage.getToUserName());
            String responseStr = "离您最近的公司为：" + wxLocation.getCompanyName() + "       地址：" + wxLocation.getCompanyAddr();
            responseMessage = XmlUtil.getResTextMessage(responseStr, baseMessage);
            // 地理位置的应答消息也要入库
            this.insertResTextMessage2DB(responseStr, baseMessage);
        }
        else if (baseMessage.getMsgType().equalsIgnoreCase("image"))
        {
            // 用户主动发送图片
            this.insertMessage2DB(requestXmlData, baseMessage);
            // 返回默认的应答文本消息
            ResMessage defaultImageMessage = resMessageService.getResMessageByAccountIdAndMessageKey("defaultImageMessage", baseMessage.getToUserName());
            String defaultImageMessageStr = "";
            if (defaultImageMessage == null)
            {
                defaultImageMessageStr = "尊敬的客户，我们已经收到您发送的图片资料。感谢您的配合，如有疑问请致电４００－８８８－８２８８。";
            }
            else
            {
                defaultImageMessageStr = defaultImageMessage.getProperties();
            }
            responseMessage = XmlUtil.getResTextMessage(defaultImageMessageStr, baseMessage);
            // 返回消息入库
            this.insertResTextMessage2DB(defaultImageMessageStr, baseMessage);
        }
        // 处理关注事件
        else if (baseMessage.getMsgType().equalsIgnoreCase("event"))
        {
            // 处理返回消息
            BaseEventMessage baseEventMessage = XmlUtil.getBaseEventMessage(requestXmlData);
            // 事件消息入库
            this.insertEventMessage2DB(requestXmlData, baseEventMessage);
            // 仅对关注事件回复消息，其余的事件不回消息
            if (baseEventMessage.getEvent().equalsIgnoreCase("subscribe"))
            {
                // 关注时查询默认的图文消息，根据不同的账号返回消息key值
                ResMessage defaultArticleMessage = resMessageService.getResMessageByAccountIdAndMessageKey(
                        ResMessageFactory.getDefaultClsubscribeArticleMessageKeyByAccountId(AppConfigProperties.getAccountId(), AppConfigProperties.getAccountId2(), baseMessage), baseMessage.getToUserName());
                // 关注时查询默认的文本消息，根据不同的账号返回消息key值
                ResMessage defaultTextMessage = resMessageService.getResMessageByAccountIdAndMessageKey(ResMessageFactory.getDefaultClsubscribeTextMessageKeyByAccountId(AppConfigProperties.getAccountId(), AppConfigProperties.getAccountId2(), baseMessage),
                        baseMessage.getToUserName());
                if (defaultArticleMessage != null && defaultTextMessage != null)
                {
                    // 配置了默认的图文消息和文本消息时，同时推送两条
                    Long articleMessageId = defaultArticleMessage.getId();
                    List<ArticleMessageItem> articleMessageItems = articleMessageItemService.getItemsByMessageId(articleMessageId);
                    // 同时响应文本和图文消息
                    responseMessage = XmlUtil.getResTextMessageJsonStr(defaultTextMessage.getProperties(), baseMessage) + "multiResMessage"
                            + XmlUtil.getResArticleMessageJsonStr(articleMessageItems, baseMessage, AppConfigProperties.getResArticleUrl());
                    this.insertResTextMessage2DB(defaultTextMessage.getProperties(), baseMessage);
                    this.insertResArticleMessage2DB(articleMessageItems, baseMessage);
                }
                else if (defaultArticleMessage == null && defaultTextMessage != null)
                {
                    // 只存在默认的文本消息
                    responseMessage = XmlUtil.getResTextMessage(defaultTextMessage.getProperties(), baseMessage);
                    // 关注时默认的应答文本消息入库
                    this.insertResTextMessage2DB(defaultTextMessage.getProperties(), baseMessage);
                }
                else if (defaultArticleMessage != null && defaultTextMessage == null)
                {
                    // 只存在默认的图文消息
                    Long articleMessageId = defaultArticleMessage.getId();
                    List<ArticleMessageItem> articleMessageItems = articleMessageItemService.getItemsByMessageId(articleMessageId);
                    responseMessage = XmlUtil.getResArticleMessage(articleMessageItems, baseMessage, AppConfigProperties.getResArticleUrl());
                    // 关注时默认的应答图文消息入库
                    this.insertResArticleMessage2DB(articleMessageItems, baseMessage);
                }
                else
                {
                    // 默认的文本消息和图文消息都不存在，返回默认的消息，不同的账号返回不同的默认文本消息
                    String defaultMessageStr = ResMessageFactory.getDefaultClsubscribeMessageByAccountId(AppConfigProperties.getAccountId(), AppConfigProperties.getAccountId2(), baseMessage);
                    responseMessage = XmlUtil.getResTextMessage(defaultMessageStr, baseMessage);
                    this.insertResTextMessage2DB(defaultMessageStr, baseMessage);
                }
                logger.info("消息响应为：" + responseMessage);
                // 关注时，需要传openId给web工程，通过调用web工程的接口将用户消息写入到用户表
                this.processUserInfo(baseMessage.getFromUserName(), baseMessage.getToUserName());
            }
            else if (baseEventMessage.getEvent().equalsIgnoreCase("unsubscribe"))
            {
                // 取消关注时修改绑定表中的状态为解除绑定
                userBindService.updateUserBindStratusByOpenId(baseMessage.getFromUserName(),"0");
                // 需要修改用户表，修改取消关注时间和状态
                WxUser wxUser = new WxUser();
                wxUser.setOpenid(baseMessage.getFromUserName());
                wxUser.setUnsubscribeTime(new Date());
                wxUser.setSubscribeFlag("unsubscribe");
                wxUser.setAccountId(baseMessage.getToUserName());
                wxUserService.updateWxUserByOpenId(wxUser);
            }
            else if (baseEventMessage.getEvent().equalsIgnoreCase("LOCATION"))
            {
                // 接收推送的地理位置事件，暂时不处理

            }
            // 处理自定义菜单click事件
            else if (baseEventMessage.getEvent().equalsIgnoreCase("CLICK"))
            {
                MenuEventMessage menuEventMessage = (MenuEventMessage) XmlUtil.getEventMessageByType("CLICK", requestXmlData);
                String eventKey = menuEventMessage.getEventKey();
                // 根据eventKey去数据库中查找消息名为eventKey的消息返回
                responseMessage = this.getResMessageByName(eventKey, baseMessage);
                // 下行消息入库
                if (StringUtils.isEmpty(responseMessage))
                {
                    // 无法找匹配到关键字时，返回默认的应答文本消息
                    ResMessage defaultMessage = resMessageService.getResMessageByAccountIdAndMessageKey(ResMessageFactory.getDefaultMessageKeyByAccountId(AppConfigProperties.getAccountId(),AppConfigProperties.getAccountId2(), baseMessage), baseMessage.getToUserName());
                    String defaultMessageStr = "";
                    // 数据库中没有配置默认的话术
                    if (defaultMessage == null)
                    {
                        defaultMessageStr = ResMessageFactory.getDefaultMessageByAccountId(AppConfigProperties.getAccountId(),AppConfigProperties.getAccountId2(), baseMessage);
                    }
                    else
                    {
                        defaultMessageStr = defaultMessage.getProperties();
                    }
                    responseMessage = XmlUtil.getResTextMessage(defaultMessageStr, baseMessage);
                    // 返回消息入库
                    this.insertResTextMessage2DB(defaultMessageStr, baseMessage);
                }
            }
            // 处理二维码扫描事件
            else if(baseEventMessage.getEvent().equalsIgnoreCase("SCAN")){
                logger.info("【二维码扫描事件】");
            }
        }
        // 组装返回消息
        return responseMessage;
    }


    /**
     * 功能描述：根据id返回图文消息内容
     * @param id
     * @return
     */
    public String getArticleMessageContent(String id)
    {
        ArticleMessageItem articleMessageItem = articleMessageItemService.selectById(id);
        if(articleMessageItem == null){
            return "";
        }
        return articleMessageItem.getContent();
    }


    /**
     * 功能描述：上行消息入库
     *
     * @param requestXmlData
     * @param baseMessage
     */
    private void insertMessage2DB(String requestXmlData, BaseMessage baseMessage) throws ParseException {
        String msgType = baseMessage.getMsgType();
        baseMessage = XmlUtil.getMessageByType(msgType, requestXmlData);
        Map<String, Object> map = new HashMap<String, Object>();
        MessageJournal messageJournal = new MessageJournal();
        if (baseMessage instanceof TextMessage)
        {
            TextMessage tmpMessage = (TextMessage) baseMessage;
            messageJournal.setProperties(Util.Unicode2GBK(tmpMessage.getContent()));
        }
        else if (baseMessage instanceof ImageMessage)
        {
            ImageMessage tmpMessage = (ImageMessage) baseMessage;
            map.put("PicUrl", tmpMessage.getPicUrl());
            map.put("MediaId", tmpMessage.getMediaId());
            messageJournal.setProperties(JsonUtil.toJSONString(map));
        }
        else if (baseMessage instanceof VoiceMessage)
        {
            VoiceMessage tmpMessage = (VoiceMessage) baseMessage;
            map.put("MediaId", tmpMessage.getMediaId());
            map.put("Format", tmpMessage.getFormat());
            messageJournal.setProperties(JsonUtil.toJSONString(map));
        }
        else if (baseMessage instanceof VedioMessage)
        {
            VedioMessage tmpMessage = (VedioMessage) baseMessage;
            map.put("MediaId", tmpMessage.getMediaId());
            map.put("ThumbMediaId", tmpMessage.getThumbMediaId());
            messageJournal.setProperties(JsonUtil.toJSONString(map));
        }
        else if (baseMessage instanceof LocationMessage)
        {
            LocationMessage tmpMessage = (LocationMessage) baseMessage;
            map.put("Location_X", tmpMessage.getLocation_X());
            map.put("Location_Y", tmpMessage.getLocation_Y());
            map.put("Scale", tmpMessage.getScale());
            map.put("Label", tmpMessage.getLabel());
            messageJournal.setProperties(JsonUtil.toJSONString(map));
        }
        else if (baseMessage instanceof LinkMessage)
        {
            LinkMessage tmpMessage = (LinkMessage) baseMessage;
            map.put("Title", tmpMessage.getTitle());
            map.put("Description", tmpMessage.getDescription());
            map.put("Url", tmpMessage.getUrl());
            messageJournal.setProperties(JsonUtil.toJSONString(map));
        }
        messageJournal.setToUserName(baseMessage.getToUserName());
        messageJournal.setFromUserName(baseMessage.getFromUserName());
        messageJournal.setCreateTime(DateUtil.getDateByString(baseMessage.getCreateTime()));
        messageJournal.setMsgId(baseMessage.getMsgId());
        messageJournal.setSendFlag("receive");
        messageJournalService.insertMessageJourna(messageJournal);
    }


    /**
     *
     * 功能描述：事件消息入库
     * @param requestXmlData
     */
    private void insertEventMessage2DB(String requestXmlData, BaseEventMessage baseEventMessage)
    {

        String event = baseEventMessage.getEvent();
        // 关注事件和扫描带参数二维码首次关注事件的event都是subscribe，因此这里必须做区分
        if (event.equalsIgnoreCase("subscribe") && requestXmlData.indexOf("EventKey") > 0)
        {
            event = "firstSubscribe";
        }
        baseEventMessage = XmlUtil.getEventMessageByType(event, requestXmlData);
        Map<String, Object> map = new HashMap<String, Object>();
        EventMessageJournal eventMessageJournal = new EventMessageJournal();
        MessageJournal messageJournal = new MessageJournal();
        if (baseEventMessage instanceof SubscribeEventMessage)
        {
            messageJournal.setProperties("[" + event + "]关注");
        }
        else if (baseEventMessage instanceof ScanEventMessage)
        {
            ScanEventMessage tmpMessage = (ScanEventMessage) baseEventMessage;
            eventMessageJournal.setEventKey(tmpMessage.getEventKey());
            eventMessageJournal.setTicket(tmpMessage.getTicket());
            messageJournal.setProperties("[" + event + "]扫描：" + tmpMessage.getEventKey());
        }
        else if (baseEventMessage instanceof LocationEventMessage)
        {
            LocationEventMessage tmpMessage = (LocationEventMessage) baseEventMessage;
            map.put("Latitude", tmpMessage.getLatitude());
            map.put("Longitude", tmpMessage.getLongitude());
            map.put("Precision", tmpMessage.getPrecision());
            messageJournal.setProperties("[" + event + "]经度：" + tmpMessage.getLongitude() + ",纬度：" + tmpMessage.getLatitude());
        }
        else if (baseEventMessage instanceof MenuEventMessage)
        {
            MenuEventMessage tmpMessage = (MenuEventMessage) baseEventMessage;
            eventMessageJournal.setEventKey(tmpMessage.getEventKey());
            messageJournal.setProperties("[" + event + "]" + tmpMessage.getEventKey());
        }
        eventMessageJournal.setToUserName(baseEventMessage.getToUserName());
        eventMessageJournal.setFromUserName(baseEventMessage.getFromUserName());
        eventMessageJournal.setCreateTime(new Date());
        eventMessageJournal.setEvent(event);
        JSONObject json = JsonUtil.mapToJSONObject(map);
        eventMessageJournal.setProperties(json.toString());
        eventMessageJournalService.insertEventMessageJournal(eventMessageJournal);

        // 同时入到消息日志表
        messageJournal.setToUserName(baseEventMessage.getToUserName());
        messageJournal.setFromUserName(baseEventMessage.getFromUserName());
        messageJournal.setCreateTime(new Date());
        messageJournal.setSendFlag("receive");
        messageJournal.setMsgType(event);
        messageJournalService.insertMessageJourna(messageJournal);
    }


    /**
     *
     * 功能描述：返回响应给用户的消息，主要有两种类型：文本类型和图文类型
     *
     * @return
     */
    private String getResMessageById(String messageId, BaseMessage baseMessage)
    {
        if (StringUtils.isEmpty(messageId))
        {
            return null;
        }
        Long msgID = NumberUtils.toLong(messageId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", msgID);
        map.put("accountId", baseMessage.getToUserName());
        ResMessage resMessage = resMessageService.getResMessageByAccountIdAndId(msgID,baseMessage.getToUserName());
        String responseMessageStr = "";
        if (resMessage != null)
        {
            if (resMessage.getMsgType().equalsIgnoreCase("text"))
            {
                String content = resMessage.getProperties();
                responseMessageStr = XmlUtil.getResTextMessage(content, baseMessage);
                // 应答文本消息入库
                this.insertResTextMessage2DB(content, baseMessage);
            }
            else if (resMessage.getMsgType().equalsIgnoreCase("news"))
            {
                Long articleMessageId = resMessage.getId();
                List<ArticleMessageItem> articleMessageItems = articleMessageItemService.getItemsByMessageId(articleMessageId);
                responseMessageStr = XmlUtil.getResArticleMessage(articleMessageItems, baseMessage, AppConfigProperties.getResArticleUrl());
                // 应答图文消息入库
                this.insertResArticleMessage2DB(articleMessageItems, baseMessage);
            }
        }
        return responseMessageStr;
    }


    /**
     * click事件消息类型返回
     *
     * 功能描述：返回响应给用户的消息，主要有两种类型：文本类型和图文类型
     *
     * @return
     */
    private String getResMessageByName(String messageName, BaseMessage baseMessage)
    {
        if (StringUtils.isEmpty(messageName))
        {
            return null;
        }
        ResMessage resMessage = resMessageService.getResMessageByAccountIdAndMessageName(messageName, baseMessage.getToUserName());
        String responseMessageStr = "";
        if (resMessage != null)
        {
            if (resMessage.getMsgType().equalsIgnoreCase("text"))
            {
                String content = resMessage.getProperties();
                responseMessageStr = XmlUtil.getResTextMessage(content, baseMessage);
                // 应答文本消息入库
                this.insertResTextMessage2DB(content, baseMessage);
            }
            else if (resMessage.getMsgType().equalsIgnoreCase("news"))
            {
                Long articleMessageId = resMessage.getId();
                List<ArticleMessageItem> articleMessageItems = articleMessageItemService.getItemsByMessageId(articleMessageId);
                responseMessageStr = XmlUtil.getResArticleMessage(articleMessageItems, baseMessage, AppConfigProperties.getResArticleUrl());
                // 应答图文消息入库
                this.insertResArticleMessage2DB(articleMessageItems, baseMessage);
            }
        }
        return responseMessageStr;
    }


    /**
     *
     * 功能描述：应答文本消息入库
     *
     * @param content
     */
    private void insertResTextMessage2DB(String content, BaseMessage baseMessage)
    {
        MessageJournal messageJournal = new MessageJournal();
        messageJournal.setToUserName(baseMessage.getFromUserName());
        messageJournal.setFromUserName(baseMessage.getToUserName());
        messageJournal.setCreateTime(new Date());
        messageJournal.setMsgType("text");
        messageJournal.setSendFlag("response");
        messageJournal.setProperties(content);
        messageJournalService.insertMessageJourna(messageJournal);
    }


    /**
     *
     * 功能描述：应答图文消息入库
     *
     * @param articleMessageItems
     * @param baseMessage
     */
    private void insertResArticleMessage2DB(List<ArticleMessageItem> articleMessageItems, BaseMessage baseMessage)
    {
        MessageJournal messageJournal = new MessageJournal();
        messageJournal.setToUserName(baseMessage.getFromUserName());
        messageJournal.setFromUserName(baseMessage.getToUserName());
        messageJournal.setCreateTime(new Date());
        messageJournal.setMsgType("news");
        messageJournal.setSendFlag("response");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (ArticleMessageItem articleMessageItem : articleMessageItems)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", articleMessageItem.getTitle());
            map.put("description", articleMessageItem.getDescription());
            map.put("picUrl", articleMessageItem.getPicUrl());
            map.put("url", articleMessageItem.getUrl());
            list.add(map);
        }
        messageJournal.setProperties(JsonUtil.toJSONString(list));
        messageJournalService.insertMessageJourna(messageJournal);
    }


    /**
     *
     * 功能描述：根据openId获取用户信息，同时将用户信息入库
     *
     * @param openId
     */
    private void processUserInfo(String openId, String toUser)
    {

        AccessToken accessToken = accessTokenService.getAccessTokenByAccountId(toUser);
        String accessTokenAndopenId = accessToken.getAccessToken() + "," + openId;
        try
        {
            logger.info("开始获得用户信息入库【accessTokenAndopenId】" + accessTokenAndopenId);
            // 调用wx接口获取用户信息
            String userInfoJson = wxApiService.getUserInfoFromApi(accessTokenAndopenId);
            if (StringUtils.isNotEmpty(userInfoJson))
            {
                JSONObject jsonObject = JsonUtil.stringToJSONObject(userInfoJson);
                WxUser wxUser = new WxUser();
                int subscribe = jsonObject.getIntValue("subscribe");
                // 关注时插入到用户表
                if (subscribe == 1)
                {
                    wxUser.setSubscribe("1");
                    wxUser.setSex(jsonObject.getString("sex"));
                    wxUser.setOpenid(jsonObject.getString("openid"));
                    wxUser.setNickname(jsonObject.getString("nickname"));
                    wxUser.setCity(jsonObject.getString("city"));
                    wxUser.setProvince(jsonObject.getString("province"));
                    wxUser.setCountry(jsonObject.getString("country"));
                    wxUser.setHeadimgurl(jsonObject.getString("headimgurl"));
                    wxUser.setSubscribeFlag("subscribe");
                    wxUser.setUnionid(jsonObject.getString("unionid"));
                    wxUser.setAccountId(toUser);
                    try
                    {
                        wxUser.setSubscribeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateUtil.convert2String(jsonObject.getLongValue("subscribe_time") * 1000L, null)));
                    }
                    catch (ParseException e)
                    {
                        logger.error("subscribe_time parse error!subscribe_time=[" + jsonObject.getString("subscribe_time") + "]", e);
                    }

                    wxUser.setUnionid(jsonObject.getString("unionid"));
                    // 用户信息入库
                    wxUserService.insertOrUpdate(wxUser);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("getUserInfo error!", e);
        }
    }


    /**
     * 功能描述：返回最近的公司地址
     * @return
     */
    private WxLocation getShortestLocation(LocationMessage locationMessage, String accountId)
    {
        Double latitude = NumberUtils.toDouble(locationMessage.getLocation_X());
        Double longitude = NumberUtils.toDouble(locationMessage.getLocation_Y());
        List<WxLocation> list = wxLocationService.getWxLocationsByAccountId(accountId);
        Double result = 10000000d;
        WxLocation wxLocation = new WxLocation();
        for (WxLocation tmpWxLocation : list)
        {
            Double tmpLatitude = NumberUtils.toDouble(tmpWxLocation.getLocationY());
            Double tmpLongitude = NumberUtils.toDouble(tmpWxLocation.getLocationX());
            Double tmpResult = Math.pow(Math.abs(latitude - tmpLatitude), 2) + Math.pow(Math.abs(longitude - tmpLongitude), 2);
            if (tmpResult < result)
            {
                result = tmpResult;
                wxLocation = tmpWxLocation;
            }
        }
        return wxLocation;
    }
}
