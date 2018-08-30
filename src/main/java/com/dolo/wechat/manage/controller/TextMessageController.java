package com.dolo.wechat.manage.controller;

import com.dolo.wechat.business.TextMessagService;
import com.dolo.wechat.common.constants.Constants;
import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.common.util.Util;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.service.IAdminUserService;
import com.dolo.wechat.service.IResMessageService;
import com.dolo.wechat.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
* 描述: 文本消息控制器
* 作者: dolojia
* 修改日期: 2018/8/30 下午11:07
* E-mail: dolojia@gmail.com
**/
@Controller
public class TextMessageController  extends BaseController
{

    @Autowired
    private IResMessageService resMessageService;

    @Autowired
    private TextMessagService textMessagService;
    
    private AdminUser adminUser;

    private IAdminUserService adminUserService;

    /**
     * 
     * 功能描述：保存文本消息
     * @param id
     * @param msgName
     * @param content
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/save_text_message", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveTextMessage(HttpServletRequest request, @RequestParam(value = "id", required = false)
    String id, String operType, @RequestParam(value = "msgName")
    String msgName, @RequestParam(value = "content")
    String content, @RequestParam(value = "keyWords")
    String keyWords, @RequestParam(value = "resType")
    String resType)
    {
        try
        {
            String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
            if (StringUtils.isEmpty(userName)){
                userName = "admin_1";
            }
            adminUser = adminUserService.getAdminUserByName(userName, null);
            this.textMessagService.saveTextMessage(userName, id, msgName, content, keyWords,resType,adminUser.getAccountId());
        }
        catch (IOException e)
        {
            return new ResultObject(-100,e.getMessage());
        }catch(Exception e){
            return new ResultObject(-1,e.getMessage());
        }
        return new ResultObject();

    }


    /**
     * 
     * 功能描述：根据id返回文本消息
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/get_text_message", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getTextMessageById(@RequestParam(value = "id")
    String id)
    {
        ResMessage resMessage = resMessageService.selectById(id);
        return new ResultObject(resMessage);

    }

}
