package com.dolo.wechat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
* 描述: 跨域设置
* 作者: dolojia
* 修改日期: 2018/8/29 14:30
* E-mail: dolojia@gmail.com
**/
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
	
	/**
	* Cors的跨域
	*/
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT")
				.maxAge(3600);
	}
}
