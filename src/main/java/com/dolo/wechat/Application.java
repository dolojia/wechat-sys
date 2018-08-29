package com.dolo.wechat;

import com.dolo.wechat.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: spring-boot启动主方法
 * 作者: dolojia
 * 修改日期: 2018/8/29 14:39
 * E-mail: dolojia@gmail.com
 **/
@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 方法名称:
     * 描述：注册过滤器
     * 作者: dolojia
     * 修改日期：2018/8/29 14:39
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(authenticationFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}
