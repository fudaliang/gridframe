package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.UserDO;
import com.softcloud.grid.privadmin.service.MenuService;
import com.softcloud.grid.privadmin.service.TokenService;
import com.softcloud.grid.privadmin.service.UserService;
import com.softcloud.grid.privadmin.utils.MD5Utils;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.dto.LoginDTO;
import com.softcloud.grid.common.dto.UserToken;
import com.softcloud.grid.common.utils.JwtUtils;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.softcloud.grid.common.annotation.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author study spring cloud
 * @version V1.0
 */
@Api(tags = {"登录接口"} )
@RequestMapping()
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    MenuService menuService;

    @Log("登录")
    @PostMapping("/login")
    R login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        String username = loginDTO.getUsername().trim();
        String password = loginDTO.getPwd().trim();
        password = MD5Utils.encrypt(username, password);
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        List<UserDO> userDOs = userService.list(param);
        if(userDOs.size()<1){
            return R.error("用户或密码错误");
        }
        UserDO userDO = userDOs.get(0);
        if (null == userDO || !userDO.getPassword().equals(password)) {
            return R.error("用户或密码错误");
        }
        
        if ( !userDO.getStatus().equals(1)  ) {
            return R.error("该用户已经禁用，请联系管理员！");
        }
        
        UserToken userToken = new UserToken(userDO.getUsername(), userDO.getUserId().toString(), userDO.getName(),userDO.getDeptId());
        String token="";
        try {
            token = JwtUtils.generateToken(userToken, 2*60*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //首先清除用户缓存权限
        menuService.clearCache(userDO.getUserId());
        //避免将密文返回给前台
        userDO.setPassword("");
        // String token = tokenService.createToken(userDO.getUserId());
        //返回结果中的操作权限和功能权限用户zuul
        //判断用户是否有这个菜单的操作权限。
       
        return R.ok("登录成功")
                .put("token", token).put("user",userDO)
                .put("perms",menuService.PermsByUserId(userDO.getUserId()))  //返回用户拥有的操作权限，   
                .put("router",menuService.RouterDTOsByUserId(userDO.getUserId()));  //返回用户拥有的菜单和功能
    }

    @ApiOperation(value = "退出系统", httpMethod ="GET" ,notes = "支持多重请求类型，常用的时get、post")
    @RequestMapping("/logout")
    R logout(HttpServletRequest request, HttpServletResponse response) {
        menuService.clearCache(Long.parseLong(FilterContextHandler.getUserID()));
        return R.ok();
    }


}
