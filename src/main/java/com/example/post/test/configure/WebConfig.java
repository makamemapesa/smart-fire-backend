package com.example.post.test.config;  // adjust package name to match your project

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // ✅ IP of your Android app/device
                .allowedMethods("*")                               // Allow all methods: GET, POST, etc.
                .allowedHeaders("*")                               // Allow all headers
                .allowCredentials(true);                           // Allow sending cookies/auth (if needed)
    }
}

