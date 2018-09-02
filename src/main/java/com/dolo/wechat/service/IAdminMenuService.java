package com.dolo.wechat.service;

import com.dolo.wechat.entity.AdminMenu;
import com.baomidou.mybatisplus.service.IService;
import com.dolo.wechat.manage.entity.TreeNode;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
public interface IAdminMenuService extends IService<AdminMenu> {

    List<TreeNode> getAdminMenuByUserName(String userName);
}
