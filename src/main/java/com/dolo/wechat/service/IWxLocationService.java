package com.dolo.wechat.service;

import com.dolo.wechat.entity.WxLocation;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IWxLocationService extends IService<WxLocation> {

    public List<WxLocation> getWxLocationsByAccountId(String accountId);

    Map<String,Object> getLocationListByPage(Integer pageNumber, Integer pageSize, HashMap<String, String> map);
}
