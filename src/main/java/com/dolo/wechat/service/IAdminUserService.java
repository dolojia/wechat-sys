package com.dolo.wechat.service;

import com.dolo.wechat.entity.AdminUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IAdminUserService extends IService<AdminUser> {

    AdminUser getAdminUserByName(String userName, String accountId);

    Map<String,Object> getAdminUserListByPage(Integer pageNumber, Integer pageSize, String userName);
}
