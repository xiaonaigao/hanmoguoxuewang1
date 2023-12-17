package com.wzl.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wang
 * @version 1.0
 * 配置类-拦截器
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
//                不让访问的页面
                .addPathPatterns("/admin/**")
//                排除的页面
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
