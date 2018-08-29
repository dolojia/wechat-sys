package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.mapper.AdminUserMapper;
import com.dolo.wechat.service.IAdminUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

}
