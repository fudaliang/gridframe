package com.softcloud.grid.gateway.controller;

import com.softcloud.grid.common.constants.CommonConstants;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.dto.MenuDTO;
import com.softcloud.grid.gateway.prc.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: LoginController <br/>
 * Function: ADD  FUNCTION. <br/>
 * @version 
 * @since JDK 1.8
 */
@RestController
public class LoginController {
    @Autowired
    MenuService menuService;
    
    /**
     * login:登陆测试用 <br/>
     *
     * @param request request
     * @return List<MenuDTO>
     * @since JDK 1.8
     */
    @GetMapping({"/test"})
    List<MenuDTO> login(ServerHttpRequest request)  {
        FilterContextHandler.setToken(request.getHeaders().getFirst(CommonConstants.CONTEXT_TOKEN));
        return menuService.userMenus();
    }
}
