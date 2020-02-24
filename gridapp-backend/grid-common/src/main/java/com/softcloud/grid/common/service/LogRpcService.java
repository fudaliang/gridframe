package com.softcloud.grid.common.service;

import com.softcloud.grid.common.dto.LogDO;
import com.softcloud.grid.common.intercepter.FeignIntercepter;
import com.softcloud.grid.common.utils.R;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;

@Headers("Content-Type:application/json")
@FeignClient(name = "grid-base", configuration = FeignIntercepter.class)
public interface LogRpcService {
    @Async
    @PostMapping("log/save")
    R save(LogDO logDO);
}
