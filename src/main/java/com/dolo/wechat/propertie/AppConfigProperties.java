package com.dolo.wechat.propertie;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 描述: 自定义properties文件内容读取
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:36
 * E-mail: dolojia@gmail.com
 **/
@Component
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {
    private static String appid;
    private static String secretKey;

    private static String accountId;
    private static String token;
    private static String appid2;
    private static String secretKey2;

    private static String accountId2;
    private static String token2;

    private static  String resArticleUrl;

    private static String lucenePath;

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        AppConfigProperties.appid = appid;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String secretKey) {
        AppConfigProperties.secretKey = secretKey;
    }

    public static String getAccountId() {
        return accountId;
    }

    public static void setAccountId(String accountId) {
        AppConfigProperties.accountId = accountId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppConfigProperties.token = token;
    }

    public static String getAppid2() {
        return appid2;
    }

    public static void setAppid2(String appid2) {
        AppConfigProperties.appid2 = appid2;
    }

    public static String getSecretKey2() {
        return secretKey2;
    }

    public static void setSecretKey2(String secretKey2) {
        AppConfigProperties.secretKey2 = secretKey2;
    }

    public static String getAccountId2() {
        return accountId2;
    }

    public static void setAccountId2(String accountId2) {
        AppConfigProperties.accountId2 = accountId2;
    }

    public static String getToken2() {
        return token2;
    }

    public static void setToken2(String token2) {
        AppConfigProperties.token2 = token2;
    }

    public static String getResArticleUrl() {
        return resArticleUrl;
    }

    public static void setResArticleUrl(String resArticleUrl) {
        AppConfigProperties.resArticleUrl = resArticleUrl;
    }

    public static String getLucenePath() {
        return lucenePath;
    }

    public static void setLucenePath(String lucenePath) {
        AppConfigProperties.lucenePath = lucenePath;
    }
}
