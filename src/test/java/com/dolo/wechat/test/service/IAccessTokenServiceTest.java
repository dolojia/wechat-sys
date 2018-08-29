package com.dolo.wechat.test.service;

import com.dolo.wechat.entity.AccessToken;
import com.dolo.wechat.service.IAccessTokenService;
import com.dolo.wechat.test.BaseTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IAccessTokenServiceTest extends BaseTests {

    @Autowired
    private IAccessTokenService accessTokenService;

    @Test
    public void testGetAccessTokenByAccountId(){
        AccessToken accessToken = accessTokenService.getAccessTokenByAccountId("123456789");
        logger.info(accessToken);
    }
}
