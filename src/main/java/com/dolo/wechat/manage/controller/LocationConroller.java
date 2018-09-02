package com.dolo.wechat.manage.controller;

import com.dolo.wechat.common.constants.Constants;
import com.dolo.wechat.common.response.ResultObject;
import com.dolo.wechat.common.util.DateUtil;
import com.dolo.wechat.common.util.Util;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.entity.WxLocation;
import com.dolo.wechat.service.IAdminUserService;
import com.dolo.wechat.service.IWxLocationService;
import com.dolo.wechat.web.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* 描述: 地址位置控制器
* 作者: dolojia
* 修改日期: 2018/9/2 下午9:06
* E-mail: dolojia@gmail.com
**/
@Controller
public class LocationConroller extends BaseController
{

    @Autowired
    private IWxLocationService wxLocationService;
    
    @Autowired
    private IAdminUserService adminUserService;

    private AdminUser adminUser;


    /**
     * 跳转到位置列表
     * 
     * @return
     */
    @RequestMapping("jump_location_list.xhtml")
    public ModelAndView jumpLocationList(@RequestParam(value = "pageNum", required = false)
    String pageNum)
    {
        return new ModelAndView("location_list").addObject("pageNum", pageNum);
    }


    /**
     * 跳转到添加编辑页面
     * 
     * @return
     */
    @RequestMapping(value = "jump_location_edit.xhtml", method = RequestMethod.GET)
    public ModelAndView jumpLocation(@RequestParam(value = "operType")
    String operType, @RequestParam(value = "id", required = false)
    String id, @RequestParam(value = "pageNum", required = false)
    String pageNum, @RequestParam(value = "locX", required = false)
    String locX, @RequestParam(value = "locY", required = false)
    String locY)
    {
         return new ModelAndView("location_edit").addObject("operType", operType).addObject("id", id).addObject("pageNum", pageNum)
         .addObject("locX", locX).addObject("locY", locY);
    }


    /**
     * 删除位置
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete_location.json", method = RequestMethod.POST)
    public ResultObject deleteLocation(@RequestParam(value = "id")
    String id)
    {
        ResultObject obj = new ResultObject();
        wxLocationService.deleteById(id);
        return obj;

    }


    /**
     * 添加位置
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add_location.json", method = RequestMethod.POST)
    public ResultObject addLocation(HttpServletRequest request, @RequestParam(value = "id", required = false)
    String id, @RequestParam(value = "operType", required = false)
    String operType, @RequestParam(value = "company", required = false)
    String companyName, @RequestParam(value = "address", required = false)
    String companyAddr, @RequestParam(value = "loc_x", required = false)
    String locationX, @RequestParam(value = "loc_y", required = false)
    String locationY)
    {
        String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
        adminUser = this.adminUserService.getAdminUserByName(userName,null);
        ResultObject obj = new ResultObject();
        WxLocation location = new WxLocation();
        location.setCompanyAddr(companyAddr);
        location.setCompanyName(companyName);
        location.setCreator(userName);
        location.setAccountId(adminUser.getAccountId());
        location.setLocationX(locationX);
        location.setLocationY(locationY);
        if (operType.equals("add"))
        {
            location.setCreateTime(new Date());
            wxLocationService.insert(location);

        }
        else if (operType.equals("edit"))
        {
            location.setUpdateTime(new Date());
            location.setId(Long.valueOf(id));
            wxLocationService.updateById(location);
        }
        return obj;

    }


    /**
     * 
     * 功能描述：加载位置信息列表
     * 
     * @param pageNumber
     * @param pageSize
     * @param companyAddr
     * @param companyName
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/load_location_list.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject loadLocationList(HttpServletRequest request,
    		@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
    		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
    		@RequestParam(value = "companyAddr", required = false) String companyAddr,
    @RequestParam(value = "companyName", required = false) String companyName,
    @RequestParam(value = "createTimeBegin", required = false) String beginTime,
    @RequestParam(value = "createTimeEnd", required = false) String endTime)
    {
    	String userName = Util.getUserNameFromCookie(request, Constants.USER_NAME_PREFIX + Constants.USER_NAME);
        adminUser = this.adminUserService.getAdminUserByName(userName,null);
        HashMap<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isEmpty(companyAddr))
        {
            map.put("companyAddr", companyAddr);
        }
        if (!StringUtils.isEmpty(companyName))
        {
            map.put("companyName", companyName);
        }
        if (!StringUtils.isEmpty(beginTime))
        {
            map.put("beginTime", beginTime);
        }
        if (!StringUtils.isEmpty(endTime))
        {
            try
            {
                endTime = DateUtil.getStringByDate(DateUtils.addDays(DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd"}), 1));
            }
            catch (ParseException e)
            {
                logger.error("request on url[load_res_message.json] parseDateExcp", e);
            }
            map.put("endTime", endTime);
        }
        map.put("accountId", adminUser.getAccountId());
        Map<String, Object> locationList = wxLocationService.getLocationListByPage(pageNumber, pageSize, map);
        return new ResultObject(locationList);

    }


    /**
     * 
     * 功能描述：加载单个位置信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/load_location_info.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject loadLocationInfo(@RequestParam(value = "id")
    Integer id)
    {
        WxLocation location = this.wxLocationService.selectById(id);
        if (location == null)
        {
            return new ResultObject(-1, "位置信息不存在!");
        }
        else
        {
            return new ResultObject(location);
        }
    }

}
