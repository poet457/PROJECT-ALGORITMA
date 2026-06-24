package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mengizinkan browser mengakses folder "uploads" di dalam proyekmu
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}