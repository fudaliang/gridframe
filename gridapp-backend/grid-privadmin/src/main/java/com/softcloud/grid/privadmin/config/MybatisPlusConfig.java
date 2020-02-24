/**
 * Project Name:gridapp-ci
 * File Name:MybatisPlusConfig.java
 * Package Name:com.softcloud.grid.ci.config
 * Date:2019年8月15日下午4:17:37
 * Copyright (c) 2019, softcloud All Rights Reserved.
 */

package com.softcloud.grid.privadmin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MybatisPlusConfig <br/>
 * Function: ADD  FUNCTION. <br/>
 * Reason: ADD  REASON. <br/>
 * @see
 * @since JDK 1.8
 */
@Configuration
@MapperScan({"com.softcloud.grid.*.dao"})
public class MybatisPlusConfig {

    /**
     * paginationInterceptor:(分页拦截). <br/>
     *
     * @return PaginationInterceptor
     * @date: 2019/8/16 9:15 <br/>
     * @since JDK 1.8
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //开启count的join优化，只针对left join
        return new PaginationInterceptor()
                .setCountSqlParser(new JsqlParserCountOptimize(true));
    }
}
