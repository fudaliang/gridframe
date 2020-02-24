package com.softcloud.grid.privadmin.config;

import com.softcloud.grid.common.intercepter.AuthIntercepter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//public class AuthConfig extends WebMvcConfigurerAdapter {
@Configuration
public class AuthConfig extends WebMvcConfigurationSupport  {
    @Value("${SWAGGER.ENABLE}")
    private boolean swaggerEnable;

    @Bean
    public AuthIntercepter authIntercepter() {
        return new AuthIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authIntercepter());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/test**");
        addInterceptor.excludePathPatterns("/swagger-resources/**");
        addInterceptor.excludePathPatterns("/v2/**");
        addInterceptor.excludePathPatterns("/swagger-ui.html**");
        addInterceptor.excludePathPatterns("/webjars/springfox-swagger-ui/**");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (swaggerEnable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
