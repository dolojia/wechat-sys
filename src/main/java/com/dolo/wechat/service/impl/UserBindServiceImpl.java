package com.dolo.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dolo.wechat.entity.UserBind;
import com.dolo.wechat.mapper.UserBindMapper;
import com.dolo.wechat.service.IUserBindService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dolojia
 * @since 2018-08-29
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements IUserBindService {

    @Autowired
    private UserBindMapper userBindMapper;

    @Override
    public int updateUserBindStratusByOpenId(String openId, String bindType) {
        UserBind userBind = new UserBind();
        EntityWrapper<UserBind> entityWrapper = new EntityWrapper<UserBind>();
        if ("0".equalsIgnoreCase(bindType)) {
            //接触绑定
            userBind.setLatestCancleBindTime(new Date());
            entityWrapper.eq("bind_flag", "1");
        } else {
            userBind.setLatestBindTime(new Date());
            entityWrapper.eq("bind_flag", "0");
        }
        entityWrapper.eq("open_id", openId);
        return userBindMapper.update(userBind, entityWrapper);
    }

    @Override
    public int updateUserBindByOpenId(UserBind userBind) {
        EntityWrapper<UserBind> entityWrapper = new EntityWrapper<UserBind>();
        entityWrapper.eq("open_id", userBind.getOpenId());
        return userBindMapper.update(userBind, entityWrapper);
    }
}
