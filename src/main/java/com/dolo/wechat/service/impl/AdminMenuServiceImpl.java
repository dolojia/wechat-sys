package com.dolo.wechat.service.impl;

import com.dolo.wechat.common.util.TreeUtils;
import com.dolo.wechat.entity.AdminMenu;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.manage.entity.TreeNode;
import com.dolo.wechat.mapper.AdminMenuMapper;
import com.dolo.wechat.service.IAdminMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolo.wechat.service.IAdminUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class AdminMenuServiceImpl extends ServiceImpl<AdminMenuMapper, AdminMenu> implements IAdminMenuService {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Autowired
    private IAdminUserService adminUserService;

    @Override
    public List<TreeNode> getAdminMenuByUserName(String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        List<AdminMenu> list = adminMenuMapper.selectByMap(map);

        AdminUser adminUser = adminUserService.getAdminUserByName(userName, null);
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        TreeNode node;
        for (AdminMenu adminMenu : list)
        {
            node = new TreeNode();
            node.setId(adminMenu.getMenuId());
            node.setName(adminMenu.getName());
            node.setParentId(adminMenu.getParentId());
            node.setHref(adminMenu.getUrl());
            node.setOrder(adminMenu.getMenuOrder());
            node.setDescription(adminMenu.getDescription());
            node.setRoles(this.getMenuRoles(adminMenu.getRole()));
            //找到所有符合条件的叶子节点
            if (node.getParentId() > 0 && (!isIncludeUserRole(node, adminUser.getRole())) && StringUtils.isNotBlank(node.getHref()))
                continue;
            nodeList.add(node);
        }
        List<TreeNode> topMenuList = TreeUtils.buildMenuTree(nodeList);
        return removeTopNodeByUserRole(topMenuList);

    }


    // 根据菜单角色返回角色列表，一个菜单可能有多个角色，但一个用户只有一个角色
    private List<String> getMenuRoles(String menuRoleStr) {
        List<String> menuRoleList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(menuRoleStr)) {
            String[] strs = StringUtils.split(menuRoleStr, ",");
            for (String s : strs) {
                menuRoleList.add(s);
            }
        }
        return menuRoleList;
    }


    //删除没有子菜单的一级菜单
    private List<TreeNode> removeTopNodeByUserRole(List<TreeNode> nodeList) {
        Iterator<TreeNode> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            TreeNode next = iterator.next();
            if (next.getChildren() == null || next.getChildren().isEmpty()) {
                iterator.remove();
            }
        }
        return nodeList;
    }

    //判断菜单叶子节点是否有角色信息，如果有，是否跟用户角色相等，admin用户除外
    private boolean isIncludeUserRole(TreeNode node, String userRole)
    {
        boolean flag = false;
        //如果叶子菜单没有角色信息则不显示
        if (node.getRoles() == null || node.getRoles().size() == 0)
            return false;
        //如果用户是admin用户，则直接返回true，可以看到所有菜单
        if(userRole.equalsIgnoreCase("admin")){
            return true;
        }
        List<String> menuRoles = node.getRoles();
        for (String s : menuRoles)
        {
            if (s.equalsIgnoreCase(userRole))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
