package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.mapper.AdminUserMapper;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.service.IAdminUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public AdminUser getAdminUserByName(String userName, String accountId) {
        AdminUser adminUser = new AdminUser();
        if (StringUtils.isEmpty(accountId)){
            accountId = AppConfigProperties.getAccountId();
        }
        adminUser.setAccountId(accountId);
        adminUser.setUserName(userName);
        return adminUserMapper.selectOne(adminUser);
    }
}
