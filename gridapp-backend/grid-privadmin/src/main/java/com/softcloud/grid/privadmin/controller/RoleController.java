package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.RoleDO;
import com.softcloud.grid.privadmin.domain.UserDO;
import com.softcloud.grid.privadmin.service.RoleService;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.utils.PageUtils;
import com.softcloud.grid.common.utils.Query;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author bootdo
 * 角色
 */
@Api(value="角色controller",tags={"角色管理接口"})
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/read")
	@ApiOperation(value = "以分页方式列出有权查看的角色清单", notes = "除了必要查询条件外，需要额外提供每页显示行数，当前需要的页数")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "跳转到的页数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页展示的记录数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "name", value = "搜索关键字", dataType = "String", required = false, paramType = "query"),
        
})
    PageUtils list(@RequestParam @ApiParam(hidden =true)Map<String, Object> params) {
    	String deptIdCreate=FilterContextHandler.getDeptId();
    	params.put("deptIdCreate", deptIdCreate);
        Query query = new Query(params);
        List<RoleDO> roleDOS = roleService.list(query);
        int total = roleService.count(query);
        PageUtils pageUtil = new PageUtils(roleDOS, total);
        return pageUtil;
    }

    @GetMapping("/userId/{userId}")
    List<Long> roleIdByUserId(@PathVariable Long userId){
        return roleService.RoleIdsByUserId(userId);
    }

    @PostMapping("/create")
    R save(@RequestBody RoleDO roleDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	roleDO.setGmtCreate(d);
    	roleDO.setUserIdCreate(Long.parseLong(FilterContextHandler.getUserID()));
    	roleDO.setDeptIdCreate(Long.parseLong(FilterContextHandler.getDeptId()));
        if(roleService.save(roleDO)>0){
            return R.ok();
        }
        return R.error();
    }

    @PutMapping("/update")
    R update(@RequestBody RoleDO roleDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	roleDO.setGmtModified(d);
        if(roleService.update(roleDO)>0){
            return R.ok();
        }
        return R.error();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    R delete(Long id){
		if (id == null ) {
			return R.error("RoleID is null");
		}
        if(roleService.remove(id)>0){
            return R.ok();
        }
        return R.error();
    }

}
