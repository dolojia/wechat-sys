package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.mapper.AdminUserMapper;
import com.dolo.wechat.propertie.AppConfigProperties;
import com.dolo.wechat.service.IAdminUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public Map<String, Object> getAdminUserListByPage(Integer pageNumber, Integer pageSize, String userName) {
        Page<AdminUser> adminUserPage = new Page<AdminUser>(pageNumber,pageSize);
        EntityWrapper<AdminUser> adminUserEntityWrapper = new EntityWrapper<>();
        adminUserEntityWrapper.like("user_name", userName);
        List<AdminUser> roleList = adminUserMapper.selectPage(adminUserPage,
                adminUserEntityWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", roleList);
        map.put("total", adminUserPage.getTotal());
        return map;
    }
}
