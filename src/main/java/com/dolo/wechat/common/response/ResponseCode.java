package com.dolo.wechat.common.response;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 描述: 响应信息
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:47
 * E-mail: dolojia@gmail.com
 **/
public final class ResponseCode {

    /**
     * 失败情况下返回code
     */
    public static final String FAIL_CODE = "1";

    /**
     * 用户不存在（主业务系统）
     */
    public static final Map<String, Object> USER_NOTEXISTS_BIZ;

    /**
     * 暂无现行合同
     */
    public static final Map<String, Object> CREDIT_ACTIVE_NOTEXISTS;

    /**
     * 查询地址失败
     */
    public static final Map<String, Object> ADDRESS_Q_FAIL;

    /**
     * 文件不存在
     */
    public static final Map<String, Object> FILE_NOT_EXIST;

    /**
     * 参数无效
     */
    public static final Map<String, Object> PARAM_INVALID;

    /**
     * 系统繁忙
     */
    public static final Map<String, Object> ERROR_COMMON;

    /**
     * 404未定义接口
     */
    public static final Map<String, Object> NO_HANDLER_FOUND;

    /**
     * 成功
     */
    public static final Map<String, Object> SUCCESS_COMMON;

    /**
     * ip
     */
    public static final Map<String, Object> IP_COMMON;

    /**
     * APPID无效
     */
    public static final Map<String, Object> APPID_INVALID;

    /**
     * 签名无效
     */
    public static final Map<String, Object> SIGN_INVALID;

    /**
     * 构建【成功】信息
     */
    private static Map<String, Object> buildSucc(String msg) {
        Map<String, Object> res = new LinkedHashMap<String, Object>();
        res.put(JsendNorm.STATUS_KEY, JsendNorm.Status.SUCCESS.toString());
        res.put(JsendNorm.DATA_KEY, msg);
        res.put(JsendNorm.MESSAGE_KEY, msg);
        res.put(JsendNorm.CODE_KEY_STRING, "10000");
        return res;
    }

    /**
     * 构建【成功】信息
     */
    public static Map<String, Object> buildSucc(Object obj) {
        Map<String, Object> res = new LinkedHashMap<String, Object>();
        res.put(JsendNorm.STATUS_KEY, JsendNorm.Status.SUCCESS.toString());
        res.put(JsendNorm.DATA_KEY, obj);
        res.put(JsendNorm.MESSAGE_KEY, JsendNorm.Status.SUCCESS.toString());
        res.put(JsendNorm.CODE_KEY_STRING, "10000");
        return res;
    }

    /**
     * 构建【成功】信息
     *
     * @param msg 提示消息
     * @param obj data数据
     * @return
     */
    public static Map<String, Object> buildSucc(String msg, Object obj) {
        Map<String, Object> res = new LinkedHashMap<String, Object>();
        res.put(JsendNorm.MESSAGE_KEY, msg);
        res.put(JsendNorm.STATUS_KEY, JsendNorm.Status.SUCCESS.toString());
        res.put(JsendNorm.DATA_KEY, obj);
        res.put(JsendNorm.CODE_KEY_STRING, "10000");
        return res;
    }

    /**
     * 构建【失败】信息
     */
    public static Map<String, Object> buildFail(String code, String msg) {
        return build(code, JsendNorm.TITLE_TOOLTIP, msg, JsendNorm.Status.FAIL.toString());
    }

    /**
     * 构建【错误】信息
     */
    public static Map<String, Object> buildError(String msg) {
        return build(null, JsendNorm.TITLE_TOOLTIP, msg, JsendNorm.Status.ERROR.toString());
    }

    /**
     * 构建【错误】信息
     */
    public static Map<String, Object> buildError(String msg, Object obj) {
        Map<String, Object> res = new LinkedHashMap<String, Object>();
        res.put(JsendNorm.MESSAGE_KEY, msg);
        res.put(JsendNorm.STATUS_KEY, JsendNorm.Status.ERROR.toString());
        res.put(JsendNorm.DATA_KEY, obj);
        return res;
    }

    /**
     * 构建信息
     */
    private static Map<String, Object> build(String code, String title, String msg, String status) {
        Map<String, Object> res = new LinkedHashMap<String, Object>();
        Map<String, String> dataMap = new LinkedHashMap<String, String>();

        if (StringUtils.isNotBlank(code)) {
            res.put(JsendNorm.CODE_KEY_STRING, code);
        }
        res.put(JsendNorm.STATUS_KEY, status);
        res.put(JsendNorm.DATA_KEY, dataMap);
        res.put(JsendNorm.MESSAGE_KEY, msg);
        return res;
    }

    static {
        // 默认success成功code为10000
        SUCCESS_COMMON = buildSucc("操作成功");
        ERROR_COMMON = buildError("系统繁忙");
        NO_HANDLER_FOUND = buildError("404未知接口异常");
        APPID_INVALID = buildFail("10001", "非法APPID");
        SIGN_INVALID = buildFail("10002", "签名无效");
        IP_COMMON = buildFail("10003", "非法ip请求");
        PARAM_INVALID = buildFail("10004", "参数无效");
        USER_NOTEXISTS_BIZ = buildFail("10005", "您还不是我们的VIP客户");
        CREDIT_ACTIVE_NOTEXISTS = buildFail("10006", "暂无现行合同");
        ADDRESS_Q_FAIL = buildFail("10007", "查询地址失败");
        FILE_NOT_EXIST = buildFail("10008", "文件不存在");
    }

}
