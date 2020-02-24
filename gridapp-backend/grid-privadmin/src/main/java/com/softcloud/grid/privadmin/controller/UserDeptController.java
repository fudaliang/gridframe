package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.MenuDO;
import com.softcloud.grid.common.dto.UserDeptDTO;

import com.softcloud.grid.privadmin.service.UserDeptService;
import com.softcloud.grid.common.annotation.Log;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.softcloud.grid.common.constants.Permission;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.dto.MenuDTO;
import com.softcloud.grid.common.utils.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 部门授权管理
 * 
 * @author xiaohei1999
 * @email 121619233@qq.com
 * @date 2019-03-18 14:40:36
 */
@Api(tags = {"资源分组授权管理"} )
@RestController
@RequestMapping("/userDeptPermission")
public class UserDeptController extends BaseController {

	@Autowired
	private UserDeptService sysDeptService;

	@Log("获取指定查询条件的部门授权列表")
	@GetMapping("/listbypage")
	@ApiOperation(value = "以分页方式列出有权查看的授权清单", notes = "除了必要查询条件外，需要额外提供每页显示行数，当前需要的页数")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "跳转到的页数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页展示的记录数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "name", value = "搜索关键字", dataType = "String", required = false, paramType = "query"),
        
})
	PageUtils list(@RequestParam @ApiParam(hidden =true)Map<String, Object> params) {
		// TODO 调用 数据检查权限，仅列出用户有权查看的部门数据 返回结果集的过滤日后统一增加
		//获取 userid 安定 dept id
		Query query = new Query(params);
		List<UserDeptDTO> sysUserDeptList = sysDeptService.list(query);
		int total = sysDeptService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserDeptList, total);
		return pageUtil;

	}

	/**
	 * 新增部门使用授权
	 */
	@ResponseBody
	@PostMapping("/create")
	public R save(@RequestBody UserDeptDTO sysDept) {

		if (sysDeptService.save(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 用来将部门、部门下面的子部门权部授权给一个人的情况
	 */
	@ResponseBody
	@PostMapping("/batchSave")
	public R batchSave(@RequestParam("userDepts[]") List<UserDeptDTO> userDepts) {

		if (sysDeptService.batchSave(userDepts) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	//@RequestMapping("/update")
	@PutMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public R update(@RequestBody UserDeptDTO sysDept) {
		
		int perm=0;
		
		if (sysDept.isReadPermission()) {
			perm =PermissionBit.grantRead(perm);
		}else {
			perm =PermissionBit.revokeRead(perm);
		}
		
		if (sysDept.isAddPermission()) {
			perm =PermissionBit.grantCreate(perm);
		}else {
			perm =PermissionBit.revokeCreate(perm);
		}

		if (sysDept.isUpdatePermission()) {
			perm =PermissionBit.grantUpdate(perm);
		}else {
			perm =PermissionBit.revokeUpdate(perm);
		}

		if (sysDept.isDeletePermission()) {
			perm =PermissionBit.grantDelete(perm);
		}else {
			perm =PermissionBit.revokeDelete(perm);
		}

		if (sysDept.isAdmPermission()) {
			perm =PermissionBit.grantAdmCMD(perm);
		}else {
			perm =PermissionBit.revokeAdmCMD(perm);
		}

		if (sysDept.isOtherCMDPermission()) {
			perm =PermissionBit.grantOthCMD(perm);
		}else {
			perm =PermissionBit.revokeOthCMD(perm);
		}
		sysDept.setPermission(perm);
			
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	@ResponseBody
	public R remove(Long id) {
		
		if (id == null ) {
			return R.error("UserDeptId is null");
		}
		
		if (sysDeptService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 批量删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public R batchRemove(@RequestParam("ids[]") Long[] ids) {
		sysDeptService.batchRemove(ids);
		return R.ok();
	}
	
    @GetMapping("userDepts")
    List<UserDeptDTO> userDepts(@RequestParam Map<String, Object> params){
    	String queryDeptId =String.valueOf(params.get("deptId"));
        List<UserDeptDTO> list = sysDeptService.userDepts(Long.parseLong(FilterContextHandler.getUserID()));

        return list;
    }

}
