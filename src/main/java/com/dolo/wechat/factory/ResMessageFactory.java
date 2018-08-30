package com.dolo.wechat.factory;

import com.dolo.wechat.recmsg.BaseMessage;

/**
 * 描述: 应答消息工厂类
 * 作者: dolojia
 * 修改日期: 2018/8/30 16:09
 * E-mail: dolojia@gmail.com
 **/
public class ResMessageFactory {

    /**
     * 功能描述：用户发上行消息时，根据账号返回不同的默认消息key
     *
     * @param accountId
     * @param accountId2
     * @param baseMessage
     * @return
     */
    public static String getDefaultMessageKeyByAccountId(String accountId, String accountId2, BaseMessage baseMessage) {
        //默认回复信息统一使用关键字defaultMessage来匹配，数据库查询时通过不同微信公众账号的accountId来检索各自的应答信息
        if (accountId.equalsIgnoreCase(baseMessage.getToUserName()) || accountId2.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "defaultMessage";
        } else {
            return null;
        }
    }


    /**
     * 功能描述：用户关注时，根据账号返回不同的默认图文消息key
     *
     * @param accountId
     * @param accountId2
     * @param baseMessage
     * @return
     */
    public static String getDefaultClsubscribeArticleMessageKeyByAccountId(String accountId, String accountId2, BaseMessage baseMessage) {
        //关注应答图片信息统一使用关键字defaultClsubscribeArticleMessage来匹配，数据库查询时通过不同微信公众账号的accountId来检索各自的应答信息
        if (accountId.equalsIgnoreCase(baseMessage.getToUserName()) || accountId2.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "defaultClsubscribeArticleMessage";
        } else {
            return null;
        }
    }

    /**
     * 功能描述：用户关注时，根据账号返回不同的默认文本消息key
     *
     * @param accountId
     * @param accountId2
     * @param baseMessage
     * @return
     */
    public static String getDefaultClsubscribeTextMessageKeyByAccountId(String accountId, String accountId2, BaseMessage baseMessage) {
        //关注应答图片信息统一使用关键字defaultClsubscribeTextMessage来匹配，数据库查询时通过不同微信公众账号的accountId来检索各自的应答信息
        if (accountId.equalsIgnoreCase(baseMessage.getToUserName()) || accountId2.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "defaultClsubscribeTextMessage";
        } else {
            return null;
        }
    }


    /**
     * 功能描述：用户发上行消息时，根据账号返回不同的下行默认文本消息
     *
     * @param accountId
     * @param accountId2
     * @param baseMessage
     * @return
     */
    public static String getDefaultMessageByAccountId(String accountId, String accountId2, BaseMessage baseMessage) {
        if (accountId.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "<a href=\"http://210.5.30.216/wxiswap/cs/1.html\">1、保单信息变更</a>" + "<a href=\"http://210.5.30.216/wxiswap/cs/2.html\">2、理赔查询</a>"
                    + "<a href=\"http://210.5.30.216/wxiswap/cs/3.html\">3、保单条款查询</a>" + "<a href=\"http://210.5.30.216/wxiswap/cs/4.html\">4、保单投递</a>"
                    + "<a href=\"http://210.5.30.216/wxiswap/cs/5.html\">5、扣费疑义</a>" + "<a href=\"http://210.5.30.216/wxiswap/cs/6.html\">6、购买需求</a>";
        } else if (accountId2.equalsIgnoreCase(baseMessage.getToUserName())) {
            return baseMessage.getToUserName() + "默认话术，代码中写死的";
        } else {
            return null;
        }
    }

    /**
     * 功能描述：用户关注时，如果数据库中没有配置任何默认的图文消息，则返回这里的默认消息
     *
     * @param accountId
     * @param accountId2
     * @param baseMessage
     * @return
     */
    public static String getDefaultClsubscribeMessageByAccountId(String accountId, String accountId2, BaseMessage baseMessage) {
        if (accountId.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "您好，感谢关注！更多功能和精彩活动不断更新中，马上开始全新体验吧！";
        } else if (accountId2.equalsIgnoreCase(baseMessage.getToUserName())) {
            return "您好，感谢关注" + baseMessage.getToUserName();
        } else {
            return null;
        }
    }


}
