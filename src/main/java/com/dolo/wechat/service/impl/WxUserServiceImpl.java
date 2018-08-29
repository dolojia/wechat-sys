package com.dolo.wechat.service.impl;

import com.dolo.wechat.entity.WxUser;
import com.dolo.wechat.mapper.WxUserMapper;
import com.dolo.wechat.service.IWxUserService;
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
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

}
