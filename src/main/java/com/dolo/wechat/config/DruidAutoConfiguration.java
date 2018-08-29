package com.dolo.wechat.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
* 描述: druid数据源配置
* 作者: dolojia
* 修改日期: 2018/8/29 14:31
* E-mail: dolojia@gmail.com
**/
@Configuration
@PropertySource(value = "classpath:druid-${spring.profiles.active}.properties")
@ConditionalOnClass(DruidDataSource.class)
public class DruidAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidAutoConfiguration.class);

    @Value("${druid.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     * 描述：实例化数据源
     * 作者: dolojia
     * 修改日期：2018/8/29 14:32
     */
    @Bean
    @ConfigurationProperties(prefix = "druid.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * 方法名称:
     * 描述：注册一个StatViewServlet
     * 作者: dolojia
     * 修改日期：2018/8/29 14:32
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle() {
        // org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
        // 添加初始化参数：initParams
        // 白名单：
        servletRegistrationBean.addInitParameter("allow", "10.14.21.169");
        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to
        // 登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "sz123456");
        // 是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 描述：注册一个：filterRegistrationBean
     * 作者: dolojia
     * 修改日期：2018/8/29 14:32
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
