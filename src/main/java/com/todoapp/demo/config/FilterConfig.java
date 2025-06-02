package com.todoapp.demo.config;

import com.todoapp.demo.filter.RequestLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering filters in the Spring Boot application.
 * This example includes a request logging filter that can be enabled or disabled
 * based on a property value.
 */
@Configuration
public class FilterConfig {

    @Value("${request.logging.enabled:true}")
    private boolean loggingEnabled;

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilterRegistration(RequestLoggingFilter filter) {
        FilterRegistrationBean<RequestLoggingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(loggingEnabled);
        registration.addUrlPatterns("/*"); // Apply to all URLs
        return registration;
    }
}