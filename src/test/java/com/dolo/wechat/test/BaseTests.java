package com.dolo.wechat.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

/**
 * @Describe:测试基础类<br>
 * 
 * @author: 100196 <br>
 * @Createdate：2017年12月1日下午5:25:43 <br>
 * @E-mail: dengjiaxing@dafycredit.com <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTests {

	protected Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	protected ResonpseTest doGet(String url) {
		ResonpseTest resonpseTest = new ResonpseTest();
		try {
			MockHttpServletRequestBuilder msrb = MockMvcRequestBuilders.get(url);
			resonpseTest = doService(msrb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resonpseTest;
	}

	protected ResonpseTest doPost(String url, String jsonData) {
		ResonpseTest resonpseTest = new ResonpseTest();
		try {
			MockHttpServletRequestBuilder msrb = MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_JSON).content(jsonData.getBytes()).characterEncoding("UTF-8");
			print(url, jsonData);
			resonpseTest = doService(msrb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resonpseTest;
	}

	protected ResonpseTest doPut(String url, String jsonData) {
		ResonpseTest resonpseTest = new ResonpseTest();
		try {
			MockHttpServletRequestBuilder msrb = MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)
					.content(jsonData.getBytes()).characterEncoding("UTF-8");
			print(url, jsonData);
			resonpseTest = doService(msrb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resonpseTest;
	}

	protected ResonpseTest doDelete(String url, String jsonData) {
		ResonpseTest resonpseTest = new ResonpseTest();
		try {
			MockHttpServletRequestBuilder msrb = MockMvcRequestBuilders.delete(url)
					.contentType(MediaType.APPLICATION_JSON).
					content(jsonData.getBytes()).
					characterEncoding("UTF-8");
			print(url, jsonData);
			resonpseTest = doService(msrb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resonpseTest;
	}

	private ResonpseTest doService(MockHttpServletRequestBuilder msrb) throws Exception {
		ResultActions ra = mockMvc.perform(msrb);
		MvcResult mr = ra.andReturn();
		String result = mr.getResponse().getContentAsString();
		print(result);
		return JSON.parseObject(result, ResonpseTest.class);
	}

	private void print(String... str) {
		for (String string : str) {
			logger.info("=========> " + string);
		}
	}

}
