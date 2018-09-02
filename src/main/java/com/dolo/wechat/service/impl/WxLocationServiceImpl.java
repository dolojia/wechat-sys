package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolo.wechat.entity.WxLocation;
import com.dolo.wechat.mapper.WxLocationMapper;
import com.dolo.wechat.service.IWxLocationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class WxLocationServiceImpl extends ServiceImpl<WxLocationMapper, WxLocation> implements IWxLocationService {

    @Autowired
    private WxLocationMapper wxLocationMapper;

    @Override
    public List<WxLocation> getWxLocationsByAccountId(String accountId) {
        Map<String, Object> map = new HashMap<>();
        map.put("account_id",accountId);
        return wxLocationMapper.selectByMap(map);
    }

    @Override
    public Map<String, Object> getLocationListByPage(Integer pageNumber, Integer pageSize, HashMap<String, String> map) {
        Page<WxLocation> adminRolePage = new Page<WxLocation>(pageNumber,pageSize);
        EntityWrapper<WxLocation> wxLocationEntityWrapper = new EntityWrapper<>();
        wxLocationEntityWrapper.like("company_name", map.get("companyName"));
        wxLocationEntityWrapper.like("company_addr",map.get("companyAddr"));
        wxLocationEntityWrapper.ge("create_time", map.get("beginTime"));
        wxLocationEntityWrapper.le("create_time", map.get("endTime"));
        wxLocationEntityWrapper.eq("account_id",map.get("accountId"));
        List<String> order = new ArrayList<>();
        order.add("create_time");
        wxLocationEntityWrapper.orderDesc(order);
        List<WxLocation> locationList =  wxLocationMapper.selectPage(adminRolePage,wxLocationEntityWrapper);

        Map<String,Object> treeMap = new TreeMap<String,Object>();
        treeMap.put("rows", locationList);
        treeMap.put("total", adminRolePage.getTotal());
        return treeMap;
    }
}
