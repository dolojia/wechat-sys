package com.dolo.wechat.manage.controller;

import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.entity.AdminRole;
import com.dolo.wechat.service.IAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
* 描述: admin角色控制器
* 作者: dolojia
* 修改日期: 2018/9/2 下午1:24
* E-mail: dolojia@gmail.com
**/
@Controller
public class AdminRoleController {

    @Autowired
    private IAdminRoleService adminRoleService;


    @RequestMapping(value = "jump_role_list.xhtml", method = RequestMethod.GET)
    public ModelAndView jumpRoleList(@RequestParam(value = "pageNum", required = false)
                                             String pageNum) {
        return new ModelAndView("role_list").addObject("pageNum", pageNum);
    }


    @RequestMapping(value = "jump_role_edit.xhtml", method = RequestMethod.GET)
    public ModelAndView jumpRoleEdit(@RequestParam(value = "operType")
                                             String operType, @RequestParam(value = "id", required = false)
                                             String id, @RequestParam(value = "pageNum", required = false)
                                             String pageNum) {
        return new ModelAndView("role_edit").addObject("operType", operType).addObject("id", id).addObject("pageNum", pageNum);
    }


    @RequestMapping(value = "/load_role_list.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject loadRoleList(@RequestParam(value = "pageNumber", defaultValue = "1")
                                             Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = "10")
                                             Integer pageSize, @RequestParam(value = "name", required = false)
                                             String name, @RequestParam(value = "code", required = false)
                                             String code) {
        Map<String, Object> roleList = this.adminRoleService.getAdminRoleList(pageNumber, pageSize, name, code);
        return new ResultObject(roleList);
    }


    @RequestMapping(value = "/save_role_info.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRoleInfo(@RequestParam(value = "id", required = false)
                                             Integer id, @RequestParam(value = "name", required = false)
                                             String name, @RequestParam(value = "code", required = true)
                                             String code, @RequestParam(value = "description", required = false)
                                             String description) {
        try {
            this.adminRoleService.insertAdminRole(id, name, code, description);
        } catch (Exception e) {
            return new ResultObject(-1, e.getMessage());
        }
        return new ResultObject();
    }


    @RequestMapping(value = "/delete_role_by_id.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject deleteRoleById(@RequestParam(value = "id")
                                               Integer id) {
        this.adminRoleService.deleteById(id);
        return new ResultObject();
    }


    @RequestMapping(value = "/get_role_by_id.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getAdminRoleById(@RequestParam(value = "id")
                                                 Integer id) {
        AdminRole adminRole = this.adminRoleService.selectById(id);
        return new ResultObject(adminRole);
    }

}
