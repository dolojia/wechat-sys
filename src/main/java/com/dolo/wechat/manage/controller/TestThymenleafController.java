package com.dolo.wechat.manage.controller;

import com.dolo.wechat.entity.ResMessage;
import com.dolo.wechat.service.IResMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestThymenleafController {

    @Autowired
    private IResMessageService resMessageService;

    @RequestMapping("/articleList.html")
    public String getArticleList(Model model) {
        Map<String,Object> map = new HashMap<>();
        map.put("1",1);
        List<ResMessage> resMessages = resMessageService.selectByMap(map);
        model.addAttribute("list", resMessages);
        return "thymeleaf";
    }

}
