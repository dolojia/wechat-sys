//package com.dolo.wechat.manage.controller;
//
//import com.dolo.wechat.web.BaseController;
//import com.dolojia.wxadmin.bo.AdminUser;
//import com.dolojia.wxadmin.bo.ArticleMessage;
//import com.dolojia.wxadmin.common.Constants;
//import com.dolojia.wxadmin.common.ResultObject;
//import com.dolojia.wxadmin.exception.BusinessException;
//import com.dolojia.wxadmin.service.AdminUserService;
//import com.dolojia.wxadmin.service.ArticleMessageService;
//import com.dolojia.wxadmin.utils.Util;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.SftpException;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//
//@Controller
//public class ArticleMessageController extends BaseController
//{
//
//    @Autowired
//    private ArticleMessageService articleMessageService;
//
//    @Autowired
//    private AdminUserService adminUserService;
//
//    private AdminUser adminUser;
//
//
//    /**
//     *
//     * 功能描述：保存图文消息
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value = "/save_article_message.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultObject saveArticleMessage(
//            @RequestBody ArticleMessage articleMessage, HttpServletRequest request)
//    {
//        try
//        {
//            String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
//            adminUser = adminUserService.getAdminUserByName(userName, null);
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            this.articleMessageService.saveArticleMessage(userName, articleMessage,path,adminUser.getAccountId());
//        }catch(IOException e){
//            return new ResultObject(-100,e.getMessage());
//        }
//        catch (BusinessException e)
//        {
//            logger.error(e.getMessage());
//            return new ResultObject(-1,e.getErrorMsg());
//        }
//        catch (SftpException e)
//        {
//            logger.error(e.getMessage());
//            return new ResultObject(-2,"图片sftp上传异常！");
//        }
//        catch (JSchException e)
//        {
//            logger.error(e.getMessage());
//            return new ResultObject(-3,"图片ftp上传异常");
//        }
//        return new ResultObject();
//
//    }
//
//
//    /**
//     *
//     * 功能描述：根据id返回图文消息
//     *
//     * @param id
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    @RequestMapping(value = "/get_article_message.json", method = RequestMethod.POST)
//    @ResponseBody
//    public ResultObject getTextMessageById(@RequestParam(value = "id")
//    String id) throws IllegalAccessException, InvocationTargetException
//    {
//        ArticleMessage articleMessage = this.articleMessageService.getArticleMessageById(id);
//        return new ResultObject(articleMessage);
//    }
//
//
//
//}
