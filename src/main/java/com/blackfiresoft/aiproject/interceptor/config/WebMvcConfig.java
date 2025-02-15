package com.blackfiresoft.aiproject.interceptor.config;

import com.blackfiresoft.aiproject.interceptor.AuthorizationInterceptor;
import com.blackfiresoft.aiproject.interceptor.BlackListInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BlackListInterceptor blackListInterceptor;
    @Resource
    private AuthorizationInterceptor authorizationInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blackListInterceptor)
                .addPathPatterns("/**")
                .order(1);
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/title/generate","/chat/*")
                .order(2);
    }
}
