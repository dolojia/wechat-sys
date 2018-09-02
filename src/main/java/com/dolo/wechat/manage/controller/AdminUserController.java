package com.dolo.wechat.manage.controller;

import com.dolo.wechat.common.constants.Constants;
import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.common.util.Util;
import com.dolo.wechat.entity.AdminRole;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.service.IAdminRoleService;
import com.dolo.wechat.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminUserController {

    @Autowired
    private IAdminUserService adminUserService;

    @Autowired
    private IAdminRoleService adminRoleService;

    /**
     * 功能描述：跳转到用户列表页面
     *
     * @return
     */
    @RequestMapping(value = "jump_admin_user_list.xhtml", method = RequestMethod.GET)
    public ModelAndView jumpAdminUserList(@RequestParam(value = "pageNum", required = false)
                                                  String pageNum) {
        return new ModelAndView("admin_user_list").addObject("pageNum", pageNum);
    }


    /**
     * 功能描述：异步加载列表数据
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_admin_user_list.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getAdminUserList(@RequestParam(value = "pageNumber", defaultValue = "1")
                                                 Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
                                                 Integer pageSize, @RequestParam(value = "userName", required = false)
                                                 String userName) {
        Map<String, Object> adminUserList = this.adminUserService.getAdminUserListByPage(pageNumber, pageSize, userName);
        return new ResultObject(adminUserList);
    }


    /**
     * 功能描述：跳转到用户编辑页面
     *
     * @param operType
     * @param id
     * @return
     */
    @RequestMapping(value = "jump_admin_user_edit.xhtml", method = RequestMethod.GET)
    public ModelAndView jumpRoleEdit(@RequestParam(value = "operType")
                                             String operType, @RequestParam(value = "id", required = false)
                                             String id, @RequestParam(value = "pageNum", required = false)
                                             String pageNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        List<AdminRole> adminRoleList = this.adminRoleService.selectByMap(map);
        return new ModelAndView("admin_user_edit").addObject("operType", operType).addObject("id", id).
                addObject("adminRoleList", adminRoleList).addObject("pageNum", pageNum).
                addObject("accountId", AppConfigProperties.getAccountId()).
                addObject("accountId2", AppConfigProperties.getAccountId2());
    }


    @RequestMapping(value = "get_admin_user_by_id.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getAdminUserById(@RequestParam(value = "id")
                                                 Integer id) {
        AdminUser adminUser = this.adminUserService.selectById(id);
        return new ResultObject(adminUser);
    }


    @RequestMapping(value = "/delete_admin_user_by_id.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject deleteRoleById(@RequestParam(value = "id")
                                               Integer id) {
        this.adminUserService.deleteById(id);
        return new ResultObject();
    }


    @RequestMapping(value = "/save_admin_user_info.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRoleInfo(@RequestParam(value = "id", required = false)
                                             Integer id, @RequestParam(value = "name", required = false)
                                             String userName, @RequestParam(value = "pwd", required = false)
                                             String password, @RequestParam(value = "role", required = false)
                                             String role, @RequestParam(value = "accountId", required = false)
                                             String accountid) {
        try {
            //警告！这里catch不到异常，总是默认返回成功
            AdminUser adminUser = new AdminUser();
            adminUser.setUserName(userName);
            adminUser.setPassword(password);
            adminUser.setRole(role);
            adminUser.setAccountId(accountid);
            this.adminUserService.insert(adminUser);
        } catch (Exception e) {
            return new ResultObject(-1, e.getMessage());
        }
        return new ResultObject();
    }


    /**
     * 修改密码
     *
     * @param oldWord
     * @return
     */
    @RequestMapping(value = "existUser.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject existUser(HttpServletRequest request, @RequestParam(value = "oldWord") String oldWord) {
        ResultObject resultObject = new ResultObject();
        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
        AdminUser user = this.adminUserService.getAdminUserByName(userName, oldWord);
        if (user == null) {
            resultObject.setData("旧密码不正确");
            return resultObject;
        } else {
            return resultObject;
        }
    }

    /**
     * 修改密码
     *
     * @param oldWord
     * @param newWord
     * @param repeatWord
     * @return
     */
    @RequestMapping(value = "changePassWord.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject changePassWord(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "oldWord") String oldWord,
                                       @RequestParam(value = "newWord") String newWord,
                                       @RequestParam(value = "repeatWord") String repeatWord) {
        ResultObject resultObject = new ResultObject();
        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
        AdminUser user = this.adminUserService.getAdminUserByName(userName, null);
        user.setPassword(newWord);
        this.adminUserService.updateById(user);
        resultObject.setData("SUCCESS");
        return resultObject;
    }

}
