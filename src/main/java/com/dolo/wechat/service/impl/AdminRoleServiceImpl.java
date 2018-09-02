package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolo.wechat.entity.AdminRole;
import com.dolo.wechat.entity.AdminUser;
import com.dolo.wechat.mapper.AdminRoleMapper;
import com.dolo.wechat.service.IAdminRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public Map<String, Object> getAdminRoleList(Integer pageNumber, Integer pageSize, String name, String code) {
        {
            Map<String,Object> fieldMmap = new HashMap<>();
            fieldMmap.put("name",name);
            fieldMmap.put("code",code);

            Page<AdminRole> adminRolePage = new Page<AdminRole>(pageNumber,pageSize);
            EntityWrapper<AdminRole> adminRoleEntityWrapper = new EntityWrapper<>();
            adminRoleEntityWrapper.like("code", code);
            adminRoleEntityWrapper.like("name",name);
            List<AdminRole> roleList = adminRoleMapper.selectPage(adminRolePage,
                    adminRoleEntityWrapper);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", roleList);
            map.put("total", adminRolePage.getTotal());
            return map;
        }
    }

    @Override
    public void insertAdminRole(Integer id, String name, String code, String description) {
        AdminRole adminRole = new AdminRole();
        adminRole.setName(name);
        adminRole.setCode(code);
        adminRole.setDescription(description);
        adminRole.setCreateTime(new Date());
        adminRoleMapper.insert(adminRole);
    }
}
