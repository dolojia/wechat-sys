package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.AccessToken;
import com.dolo.wechat.mapper.AccessTokenMapper;
import com.dolo.wechat.service.IAccessTokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken> implements IAccessTokenService {

    @Autowired
    private AccessTokenMapper accessTokenMapper;

    @Override
    public AccessToken getAccessTokenByAccountId(String accountId) {
        AccessToken accessToken= new AccessToken();
        accessToken.setAccountId(accountId);
        return accessTokenMapper.selectOne(accessToken);
    }
}
