package com.yu.jwtserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 서버 응답 시 json을 자바스크립트에서 처리할 수 있도록
        configuration.addAllowedOrigin("*"); // 모든 ip에 허용
        configuration.addExposedHeader("*"); // 모든 헤더에 허용
        configuration.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청 허용
        source.registerCorsConfiguration("/api/**", configuration);
        return new CorsFilter(source);
    }
}
