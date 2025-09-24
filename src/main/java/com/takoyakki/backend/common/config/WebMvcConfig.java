package com.takoyakki.backend.common.config;

import com.takoyakki.backend.common.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .excludePathPatterns("/**")
                .excludePathPatterns("/v1/auth/signUp")
                .order(1);
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://siathub.com",
                        "https://siathub.com",
                        "http://siathub.com.s3-website.ap-northeast-2.amazonaws.com"
                )
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
