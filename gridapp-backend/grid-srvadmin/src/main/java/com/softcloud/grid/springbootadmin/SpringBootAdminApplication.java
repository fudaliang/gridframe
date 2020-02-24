package com.softcloud.grid.springbootadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * ClassName: SpringBootAdminApplication <br/>
 * Function: SpringBoot应用启动类 <br/>
 * date: 2019年10月13日 下午12:53:48 <br/>
 *
 * @author juvenbin
 * @version 
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class SpringBootAdminApplication {

    /**
     * main:启动入口 <br/>
     *
     * date: 2019年6月9日 下午4:43:56
     * @author juvensun
     * @param args 启动参数
     * @since JDK 1.8
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }

}
