package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.WxLocation;
import com.dolo.wechat.mapper.WxLocationMapper;
import com.dolo.wechat.service.IWxLocationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
