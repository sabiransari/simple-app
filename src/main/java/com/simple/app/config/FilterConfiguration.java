package com.simple.app.config;

import com.simple.app.filter.AuditTrailFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean<AuditTrailFilter> auditTrailFilter() {
        FilterRegistrationBean<AuditTrailFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new AuditTrailFilter());
        filterBean.set
    }
}
