package com.test.csv.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DruidDBConfig
 * @Description 自定义DataSource
 * @Author QiaoFoPing
 * @Date 2020/12/17 17:21
 * @Version 1.0
 */
@Configuration
public class DruidDBConfig {
    private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary //被注入的优先级最高
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        logger.info("------>datasource[url="+dbUrl+",username="+username+"]");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
    @Bean
    public ServletRegistrationBean druidServletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }
    /**
     * @description 注册DruidFilter拦截
     * @author  Qiaofoping
     * @date    2020/12/18 10:14
     * @param
     * @return
    */
    @Bean
    public FilterRegistrationBean druidFilterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        //设置忽略请求
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}