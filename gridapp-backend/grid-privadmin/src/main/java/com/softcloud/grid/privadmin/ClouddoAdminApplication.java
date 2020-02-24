package com.softcloud.grid.privadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = {"com.softcloud.grid"})
@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.softcloud.grid.privadmin", "com.softcloud.grid.common"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ClouddoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClouddoAdminApplication.class, args);
    }

}
