package com.softcloud.grid.common.intercepter;

import com.softcloud.grid.common.constants.CommonConstants;
import com.softcloud.grid.common.context.FilterContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignIntercepter implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("------feign设置token" + Thread.currentThread().getId());
        requestTemplate.header(CommonConstants.CONTEXT_TOKEN, FilterContextHandler.getToken());
    }
}