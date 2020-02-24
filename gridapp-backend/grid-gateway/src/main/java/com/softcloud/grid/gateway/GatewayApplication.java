package com.softcloud.grid.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.softcloud.grid.gateway.filter.PreAccessFilter;

/**
 * ClassName: GatewayApplication <br/>
 * Function: 应用启动类 <br/>

 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GatewayApplication {

    /**
     * main:启动入口 <br/>
     *
     * @param args 启动参数
     * @since JDK 1.8
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    
    /**
     * accessFilter:启动accessFilter <br/>
     *
     * @return PreAccessFilter
     * @since JDK 1.8
     */
    @Bean
    public PreAccessFilter accessFilter(){
        return new PreAccessFilter();
    }
}
