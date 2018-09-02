package com.dolo.wechat.manage.controller;

import com.dolo.wechat.common.constants.Constants;
import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.common.util.Util;
import com.dolo.wechat.manage.entity.TreeNode;
import com.dolo.wechat.service.IAdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 登陆Controller
 * 
 * @author j1deng
 */
@Controller
public class LoginController
{
    @Autowired
    private IAdminMenuService adminMenuService;

    @GetMapping(value = "/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping(value = "/login.xhtml", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "userName")String userName, @RequestParam(value = "pwd")String pwd)
    {
        List<TreeNode> adminMenu = adminMenuService.getAdminMenuByUserName(userName);
        return new ModelAndView("index").addObject("userName",userName).addObject("adminMenu", adminMenu);
    }




    /**
     * 退出登陆
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logOut.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject logOut(HttpServletRequest request, HttpServletResponse response)
    {
        Util.clearCookie(request, response, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
        ResultObject resultObject = new ResultObject();
        resultObject.setData("SUCCESS");
        return resultObject;
    }
    

}
