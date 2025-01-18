package com.neu.csye6220.parkmate.config;

import com.neu.csye6220.parkmate.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register the interceptor and specify the paths to apply it to
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/renter/**", "/rentee/**") // Apply to these routes
                .excludePathPatterns("/register", "/register/renter", "/register/rentee", "/login", "/logout", "/error", "/static/**", "/access-denied"); // Exclude public paths
    }
}
