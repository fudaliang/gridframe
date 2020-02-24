package com.softcloud.grid.gateway.filter;

import com.softcloud.grid.common.constants.CommonConstants;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.dto.MenuDTO;
import com.softcloud.grid.common.dto.UserToken;
import com.softcloud.grid.common.utils.JSONUtils;
import com.softcloud.grid.common.utils.JwtUtils;
import com.softcloud.grid.common.utils.R;
import com.softcloud.grid.gateway.prc.admin.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @version V1.0
 */
public class PreAccessFilter implements GlobalFilter, Ordered {
    @Autowired
    MenuService menuService;

    private String[] ignorePath = {"/priv-admin/login", "/api-base/quality/listConfigQuality", 
            "/api-ci/module/listModuleIdByModuleName", "/api-ci/module/countModuleIdByModuleName", 
            "/api-cd/deployTask/updateDepJobStatus", "/api-base/vmware/downloadVmExcelTemplate",
            "/api-cd/deployPara/downloadParaConfigTemplate"};

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        //获取请求路径
        final String requestUri = request.getURI().getPath();
        //判断请求路径是否以忽略路径开头
        if (isStartWith(requestUri)) {
            return chain.filter(exchange);
        }
        //获取Authorization值
        String accessToken = request.getHeaders().getFirst(CommonConstants.CONTEXT_TOKEN);
        if (null == accessToken || accessToken == "") {
            //获取token值
            accessToken = request.getQueryParams().getFirst(CommonConstants.TOKEN);
        }
        if (null == accessToken) {
            return setFailedRequest(exchange, R.error401());
        }
        UserToken userToken;
        try {
            userToken = JwtUtils.getInfoFromToken(accessToken);
        } catch (Exception e) {
            return setFailedRequest(exchange, R.error401());
        }
        FilterContextHandler.setToken(accessToken);

        if (!haveOperationPermission(request)) {
            return setFailedRequest(exchange, R.error403());
        }


        if (!haveDeptAccessPermission(request, userToken)) {
            return setFailedRequest(exchange, R.error(403, "no data access permission"));
        }

        return chain.filter(exchange);
    }

    /**
     * setFailedRequest:设置错误请求 <br/>
     * <p>
     * date: 2019年10月12日 下午4:21:32
     *
     * @param exchange exchange
     * @param body     body
     * @return Mono<Void>
     * @since JDK 1.8
     */
    private Mono<Void> setFailedRequest(ServerWebExchange exchange, Object body) {
        String errorMessage = JSONUtils.beanToJson(body);
        ServerHttpResponse response = exchange.getResponse();
        return response.writeWith(Flux.just(errorMessage).map(bx -> response.bufferFactory().wrap(bx.getBytes())));
    }

    /**
     * haveOperationPermission:过滤操作权限 <br/>
     * <p>
     * date: 2019年10月12日 下午4:20:11
     *
     * @param request 请求对象
     * @return True：已授权，False：未授权
     * @since JDK 1.8
     */
    private boolean haveOperationPermission(ServerHttpRequest request) {
        String currentURL = request.getURI().getPath();
        List<MenuDTO> menuDTOSPublic = menuService.publicMenus();
        for (MenuDTO menuDTO : menuDTOSPublic) {
            if (currentURL != null && null != menuDTO.getUrl() && currentURL.equals(menuDTO.getUrl())) {
                return true;
            }
        }
        List<MenuDTO> menuDTOSUser = menuService.userMenus();
        for (MenuDTO menuDTO : menuDTOSUser) {
            if (currentURL != null && null != menuDTO.getUrl() && currentURL.equals(menuDTO.getUrl())) {
                return true;
            }
        }
        return false;
    }

    /**
     * haveDeptAccessPermission:haveDeptAccessPermission <br/>
     * <p>
     * date: 2019年10月12日 下午4:19:20
     *
     * @param request 请求对象
     * @param token   token
     * @return boolean
     * @since JDK 1.8
     */
    private boolean haveDeptAccessPermission(ServerHttpRequest request, UserToken token) {
        return true;//据说是测试函数
    }

    /**
     * isStartWith:过滤URL <br/>
     * <p>
     * date: 2019年10月12日 下午4:17:48
     *
     * @param requestUri 请求URL
     * @return 是否例外
     * @since JDK 1.8
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : ignorePath) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        //websocket
        if (requestUri.contains("ws-api")) {
            return true;
        }
        return flag;
    }


}
