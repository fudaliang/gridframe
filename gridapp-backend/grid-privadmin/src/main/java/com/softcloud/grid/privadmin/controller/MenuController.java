package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.MenuDO;
import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.privadmin.service.MenuService;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.dto.MenuDTO;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;

import com.softcloud.grid.common.annotation.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author study spring cloud
 * @version V1.0
 */
@Api(tags = {"系统功能菜单管理"} )
@RequestMapping("/menu")
@RestController()
public class MenuController {
    @Autowired
    MenuService menuService;

    @Log("访问菜单")
    @GetMapping("tree")
    Tree<MenuDO>  tree(){
        return menuService.getTree();
    }
    @GetMapping("/read")
    List<Tree<MenuDO>>  list(){
        return menuService.getTree().getChildren();
    }

    @GetMapping("{id}")
    MenuDO get(@PathVariable("id") Long id) {
        MenuDO menu = menuService.get(id);
        return menu;
    }

    @GetMapping("list")
    List<MenuDO> list(@RequestParam Map<String, Object> params) {
        List<MenuDO> menus = menuService.list(params);
        return menus;
    }

    @PutMapping("/update")
    R update(@RequestBody MenuDO menuDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	menuDO.setGmtModified(d);
        if(menuService.update(menuDO)>0){
            return R.ok();
        }
        return  R.error();
    }
    @PostMapping("/create")
    R save(@RequestBody MenuDO menuDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	menuDO.setGmtCreate(d);
        return R.operate(menuService.save(menuDO)>0);
    }

    @DeleteMapping("/delete")
    R remove(Long id){
        if(menuService.remove(id)>0){
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("userMenus")
    List<MenuDTO> userMenus(){
        List<MenuDO> menuDOS = menuService.userMenus(Long.parseLong(FilterContextHandler.getUserID()));
        List<MenuDTO> menuDTOS = new ArrayList<>();
        for (MenuDO menuDO:menuDOS){
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuId(menuDO.getMenuId());
            menuDTO.setUrl(menuDO.getUrl());
            menuDTO.setPerms(menuDO.getPerms());
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }
    
    @GetMapping("publicMenus")
    List<MenuDTO> publicMenus(){
        List<MenuDO> menuDOS = menuService.publicMenus();
        List<MenuDTO> menuDTOS = new ArrayList<>();
        for (MenuDO menuDO:menuDOS){
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuId(menuDO.getMenuId());
            menuDTO.setUrl(menuDO.getUrl());
            menuDTO.setPerms(menuDO.getPerms());
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }
    /**
     * 当前用户菜单的树形结构
     * @return
     */
    @GetMapping("/currentUserMenus")
    List<Tree<MenuDO>> currentUserMenus() {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(Long.parseLong(FilterContextHandler.getUserID()));
        return menus;
    }
    
    @GetMapping("clearCache")
    R clearCache(){
        Boolean flag = menuService.clearCache(Long.parseLong(FilterContextHandler.getUserID()));
        if (flag){
            return  R.ok();
        }
        return R.error();
    }



    @GetMapping("/roleId")
    List<Long> menuIdsByRoleId(Long roleId){
        return menuService.MenuIdsByRoleId(roleId);
    }
}
