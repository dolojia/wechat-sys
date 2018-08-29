package com.dolo.wechat.service;

import com.baomidou.mybatisplus.service.IService;
import com.dolo.wechat.entity.AccessToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IAccessTokenService extends IService<AccessToken> {

    /**
     * 描述：根据accountId获取token
     * 作者: dolojia
     * 修改日期：2018/8/29 17:04
     */
    public AccessToken getAccessTokenByAccountId(String accountId);

}
