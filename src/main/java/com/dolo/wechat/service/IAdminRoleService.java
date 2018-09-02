package com.dolo.wechat.service;

import com.dolo.wechat.entity.AdminRole;
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
public interface IAdminRoleService extends IService<AdminRole> {

    Map<String,Object> getAdminRoleList(Integer pageNumber, Integer pageSize, String name, String code);

    void insertAdminRole(Integer id, String name, String code, String description);
}
