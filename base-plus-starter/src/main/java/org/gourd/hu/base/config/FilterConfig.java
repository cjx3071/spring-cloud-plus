package org.gourd.hu.base.config;

import org.gourd.hu.base.request.filter.RequestDetailFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 过滤器配置
 *
 * @author gourd.hu
 */
public class FilterConfig {

    @Autowired
    private RequestDetailFilter requestDetailFilter;

    @Bean
    public FilterRegistrationBean registerAuthFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestDetailFilter);
        registration.addUrlPatterns("/*");
        registration.setName("requestDetailFilter");
        // 值越小，Filter越靠前。
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}