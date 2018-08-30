//package com.dolo.wechat.manage.controller;
//
//import com.dolo.wechat.web.BaseController;
//import com.dolojia.wxadmin.bo.AdminUser;
//import com.dolojia.wxadmin.common.Constants;
//import com.dolojia.wxadmin.common.MessageTypeEnum;
//import com.dolojia.wxadmin.common.ResultObject;
//import com.dolojia.wxadmin.service.AdminUserService;
//import com.dolojia.wxadmin.service.ResMessagService;
//import com.dolojia.wxadmin.utils.Util;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Map;
//
///**
//* 描述: 回消息控制器
//* 作者: dolojia
//* 修改日期: 2018/8/30 下午11:07
//* E-mail: dolojia@gmail.com
//**/
//@Controller
//public class ResMessageController  extends BaseController
//{
//
//    @Autowired
//    private ResMessagService resMessagService;
//
//    @Autowired
//    private AdminUserService adminUserService;
//
//    private AdminUser adminUser;
//
//
//    /**
//     * 跳转至消息管理页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "jump_res_message_list.xhtml", method = RequestMethod.GET)
//    public ModelAndView jumpResMessageList(@RequestParam(value = "pageNum", required = false)
//    String pageNum)
//    {
//        return new ModelAndView("res_message_list").addObject("pageNum", pageNum);
//    }
//
//
//    /**
//     *
//     * 功能描述：根据不同的消息类型扭转到不同的页面
//     * @param request
//     * @param operType，操作类型
//     * @param id，消息id
//     * @param status，审核状态
//     * @param msgType，消息类型
//     * @param pageNum，当前页码
//     * @return
//     */
//    @RequestMapping(value = "jump_message_edit.xhtml", method = RequestMethod.GET)
//    public ModelAndView jumpTextMessage(HttpServletRequest request, @RequestParam(value = "operType")
//    String operType, @RequestParam(value = "id", required = false)
//    String id, @RequestParam(value = "status", required = false)
//    String status, @RequestParam(value = "msgType", required = false)
//    String msgType, @RequestParam(value = "pageNum", required = false)
//    String pageNum)
//    {
//        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
//        String role = this.adminUserService.getUserRoleByName(userName);
//        if (msgType.equalsIgnoreCase(MessageTypeEnum.NEWS.name()))
//        {
//            return new ModelAndView("article_message_edit").addObject("operType", operType).addObject("id", id).addObject("status", status == null ? "" : status.toLowerCase())
//                .addObject("pageNum", pageNum).addObject("role", role);
//        }
//        else if(msgType.equalsIgnoreCase(MessageTypeEnum.TEXT.name()))
//        {
//            return new ModelAndView("text_message_edit").addObject("operType", operType).addObject("id", id).addObject("status", status == null ? "" : status.toLowerCase())
//                .addObject("pageNum", pageNum).addObject("role", role);
//        }else{
//            return new ModelAndView("multi_article_message_edit").addObject("operType", operType).addObject("id", id).addObject("status", status == null ? "" : status.toLowerCase())
//                    .addObject("pageNum", pageNum).addObject("role", role);
//        }
//    }
//
//
//    /**
//     *
//     * 功能描述：加载消息列表的数据
//     *
//     * @return
//     */
//    @RequestMapping(value = "/load_res_message.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultObject loadResMessage(HttpServletRequest request, @RequestParam(value = "pageNumber", defaultValue = "1")
//    Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
//    Integer pageSize, @RequestParam(value = "querySatus", required = false)
//    String status, @RequestParam(value = "queryName", required = false)
//    String msgName, @RequestParam(value = "createTimeBegin", required = false)
//    String beginTime, @RequestParam(value = "createTimeEnd", required = false)
//    String endTime, @RequestParam(value = "queryKeyWords", required = false)
//    String keyWords, @RequestParam(value = "queryType", required = false)
//    String msgType, @RequestParam(value = "resType", required = false)
//    String resType)
//    {
//        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
//        adminUser = adminUserService.getAdminUserByName(userName, null);
//        Map<String, Object> resMessageList = this.resMessagService.getResMessageList(userName, pageNumber, pageSize, status, msgName, beginTime, endTime, keyWords, msgType, resType,adminUser.getAccountId());
//        return new ResultObject(resMessageList);
//    }
//
//
//    /**
//     *
//     * 功能描述：修改消息的状态
//     *
//     * @param id
//     * @param status
//     * @return
//     */
//    @RequestMapping(value = "/update_message_status.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultObject controlResMessage(@RequestParam(value = "id")
//    String id, @RequestParam(value = "status")
//    String status)
//    {
//        this.resMessagService.updateMessageStatusById(id, status);
//        return new ResultObject();
//
//    }
//
//
//    /**
//     *
//     * 功能描述：删除消息
//     *
//     * @param id
//     * @param msgName
//     * @param content
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value = "/delete_res_message.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultObject deleteTextMessageById(@RequestParam(value = "id")
//    String id, @RequestParam(value = "msgType")
//    String msgType)
//    {
//        try
//        {
//            this.resMessagService.deleteMessageById(id, msgType);
//        }
//        catch (IOException e)
//        {
//            return new ResultObject(-100, e.getMessage());
//        }
//        return new ResultObject();
//
//    }
//
//}
