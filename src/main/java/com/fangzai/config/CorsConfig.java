package com.fangzai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 创建基于URL的CORS配置源
        CorsConfiguration config = new CorsConfiguration(); // 创建CORS配置
        config.setAllowCredentials(true); // 允许发送认证信息（例如，Cookie）
        config.addAllowedOrigin("http://localhost:9528"); // 允许的前端域名或IP
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addAllowedMethod("*"); // 允许所有HTTP方法（GET、POST、PUT、DELETE等）
        source.registerCorsConfiguration("/**", config); // 将CORS配置应用于所有URL路径
        return new CorsFilter(source); // 创建CORS过滤器并返回
    }
}
