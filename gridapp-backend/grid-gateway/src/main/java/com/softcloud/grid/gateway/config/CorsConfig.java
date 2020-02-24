package com.softcloud.grid.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * ClassName: CorsConfig <br/>
 * Function: ADD FUNCTION. <br/>
 * @version
 * @since JDK 1.8
 */
@Configuration
public class CorsConfig {

    /**
     * corsFilter:初始化跨域资源共享配置 <br/>
     * @return CorsWebFilter
     * @since JDK 1.8
     */
    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsWebFilter(source);
    }

    /**
     * buildConfig:初始化跨域配置 <br/>
     * @return CorsConfiguration
     * @since JDK 1.8
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域,支持安全证书
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        config.setMaxAge(3600L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");// 允许Get的请求方法
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        return config;
    }
}