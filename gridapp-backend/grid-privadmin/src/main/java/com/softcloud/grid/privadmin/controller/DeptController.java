package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.DeptDO;
import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.privadmin.service.DeptService;
import com.softcloud.grid.common.utils.PageUtils;
import com.softcloud.grid.common.utils.Query;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.softcloud.grid.common.annotation.Log;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"资源分组管理"} )
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
	private String prefix = "system/dept";
	@Autowired
	private DeptService sysDeptService;

	@Log("获取部门列表")
	@GetMapping("/read")
	@ApiOperation(value = "以分页方式列出有权查看的资源分组清单", notes = "除了必要查询条件外，需要额外提供每页显示行数，当前需要的页数")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "跳转到的页数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页展示的记录数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "name", value = "搜索关键字", dataType = "String", required = false, paramType = "query"),
        
})
	PageUtils list(@RequestParam @ApiParam(hidden =true)Map<String, Object> params) {
		// todo 调用 数据检查权限，仅列出用户有权查看的部门数据
		Query query = new Query(params);
		List<DeptDO> sysDeptList = sysDeptService.listChildDept(query);
		int total = sysDeptService.countChild(query);
		PageUtils pageUtil = new PageUtils(sysDeptList, total);
		return pageUtil;

	}



	/**
	 * 新增部门
	 */
	@ResponseBody
	@PostMapping("/create")
	@RequiresPermissions("system:sysDept:add")
	public R save(@RequestBody DeptDO sysDept) {

		// todo 调用 数据检查权限，判断当前操作用户是否有权限在这个部门下提交数据

		if (sysDeptService.save(sysDept) > 0) {
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
	public R update(@RequestBody DeptDO sysDept) {
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	// @PostMapping("/remove")
	@DeleteMapping("/delete")
	@ResponseBody
	@RequiresPermissions("system:sysDept:remove")
	public R remove(Long deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if (sysDeptService.count(map) > 0) {
			return R.error(1, "包含下级部门,不允许修改");
		}
		if (sysDeptService.checkDeptHasUser(deptId)) {
			if (sysDeptService.remove(deptId) > 0) {
				return R.ok();
			}
		} else {
			return R.error(1, "部门包含用户,不允许修改");
		}
		return R.error();
	}

	/**
	 * 批量删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] deptIds) {
		sysDeptService.batchRemove(deptIds);
		return R.ok();
	}

	@GetMapping("/childTree")
	@ResponseBody
	public List<Tree<DeptDO>> tree(@RequestParam Map<String, Object> params) {
		Tree<DeptDO> tree = sysDeptService.getTree(params);
		return tree.getChildren();
	}

	@GetMapping("/treeView")
	String treeView() {
		return prefix + "/deptTree";
	}

}
