package com.dolo.wechat.service;

import com.dolo.wechat.entity.WxUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IWxUserService extends IService<WxUser> {

    public Integer updateWxUserByOpenId(WxUser wxUser);

    public WxUser getWxUserByOpenId(String openId);

}
