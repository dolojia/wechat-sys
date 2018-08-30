package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dolo.wechat.entity.UserBind;
import com.dolo.wechat.entity.WxUser;
import com.dolo.wechat.mapper.WxUserMapper;
import com.dolo.wechat.service.IWxUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public Integer updateWxUserByOpenId(WxUser wxUser) {
        EntityWrapper<WxUser> entityWrapper = new EntityWrapper<WxUser>();

        entityWrapper.eq("open_id", wxUser.getOpenid());
        return wxUserMapper.update(wxUser,entityWrapper);
    }

    @Override
    public WxUser getWxUserByOpenId(String openId) {
        WxUser wxUser = new WxUser();
        wxUser.setOpenid(openId);
        return wxUserMapper.selectOne(wxUser);
    }
}
