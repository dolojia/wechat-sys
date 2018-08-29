package com.dolo.wechat.web;

import com.dolo.wechat.common.valid.ValidField;
import com.dolo.wechat.common.valid.ValidMap;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <br>TODO(描述该类的作用)</br>
 *
 * @author 533735
 * @version 1.0
 * @email wanghong3@dafycredit.com
 * @date 2018/7/18 10:56
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/personal")
@Api(value = "UserController", tags = {"用户信息相关接口"})
public class UserController {

    /**
     * 查询汇总CRM客户信息
     *
     * @param paramMap
     * @return
     */
    @ValidMap(fields = {@ValidField(fieldName = "idPerson", notBlank = true, msg = "idPerson不能为空！")})
    @PostMapping(value = "/applyInformation")
    public Map<String, Object> applyInformation(@RequestBody Map<String, String> paramMap) {
        return null;
    }

}
