package com.softcloud.grid.gateway.prc.admin;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.softcloud.grid.common.dto.MenuDTO;
import com.softcloud.grid.common.intercepter.FeignIntercepter;


//@Headers({"Content-Type:application/json", "Accept: application/json"})
@FeignClient(name = "grid-privadmin", configuration = FeignIntercepter.class)
public interface MenuService {
    @GetMapping("/menu/userMenus")
    List<MenuDTO> userMenus();

    @GetMapping("/menu/publicMenus")
    List<MenuDTO> publicMenus();
}
