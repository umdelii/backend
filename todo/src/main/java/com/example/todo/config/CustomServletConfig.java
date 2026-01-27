package com.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS : 전역설정
@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // http://localhost:5173을 쓰면 5173허용할거임이라는 뜻
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .maxAge(300) // default 1800초, 서버응답 얼마나 기다릴건지
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type"); // headers 정보 허용할 항목
    }

}
