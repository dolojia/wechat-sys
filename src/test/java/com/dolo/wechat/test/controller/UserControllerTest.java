package com.dolo.wechat.test.controller;

import com.alibaba.fastjson.JSON;
import com.dolo.wechat.test.BaseTests;
import com.dolo.wechat.web.UserController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <br>TODO(描述该类的作用)</br>
 *
 * @author 533735
 * @version 1.0
 * @email wanghong3@dafycredit.com
 * @date 2018/7/19 11:47
 * @since 1.0
 */
public class UserControllerTest  extends BaseTests {
    @Autowired
    private UserController userController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void testGetPersonalBaseInfo(){
        String url = "/personal/getPersonalBaseInfo";
        Map<String,Object> param = new HashMap<>();
        param.put("idPerson","10000461");
        assertEquals("success", super.doPost(url, JSON.toJSONString(param)).getStatus());
    }

    @Test
    public void testGetContactInfo(){
        String url = "/personal/getContactInfo";
        Map<String,Object> param = new HashMap<>();
        param.put("idPerson","10000461");
        assertEquals("success", super.doPost(url, JSON.toJSONString(param)).getStatus());
    }

    @Test
    public void testGetCompanyInfo(){
        String url = "/personal/getCompanyInfo";
        Map<String,Object> param = new HashMap<>();
        param.put("idPerson","10000461");
        assertEquals("success", super.doPost(url, JSON.toJSONString(param)).getStatus());
    }

    @Test
    public void testGetExperienceInfo(){
        String url = "/personal/getExperienceInfo";
        Map<String,Object> param = new HashMap<>();
        param.put("idPerson","10000461");
        assertEquals("success", super.doPost(url, JSON.toJSONString(param)).getStatus());
    }

}
