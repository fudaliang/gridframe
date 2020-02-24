package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.UserDO;
import com.softcloud.grid.privadmin.dto.UserDTO;
import com.softcloud.grid.privadmin.dto.do2dto.UserConvert;
import com.softcloud.grid.privadmin.service.RoleService;
import com.softcloud.grid.privadmin.service.UserService;
import com.softcloud.grid.privadmin.utils.MD5Utils;
import com.softcloud.grid.common.dto.LoginDTO;
import com.softcloud.grid.common.dto.LoginUserDTO;
import com.softcloud.grid.common.utils.PageUtils;
import com.softcloud.grid.common.utils.Query;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.softcloud.grid.common.annotation.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author bootdo
 */
@Api(tags = {"系统用户管理"} )
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

	/**
	 * 登录的当前用户，前台需要验证用户登录的页面可以调用此方法
	 * @return
	 */
    @GetMapping("/currentUser")
	LoginUserDTO currentUser(){
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUserId(super.getUserID());
		loginUserDTO.setUsername(super.getUserName());
		loginUserDTO.setName(super.getName());
		loginUserDTO.setDeptId(Long.parseLong(super.getDeptId()));
		
		return loginUserDTO;
	}

	/**
	 *  h 为了兼容vue-element-admin前端，这个用法获取当前用户的权限列表，并组织成角色清单形式
	 *  h概念上，把对一个对象的读、写、执行等操作定义为不同的角色。形如，模块。操作角色
	 *  such as: ['admin.create'] or ,['cms.delete','cms.read']
	 * @return
	 */
    @GetMapping("/getInfo")
	LoginUserDTO getInfo(){
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUserId(super.getUserID());
		loginUserDTO.setUsername(super.getUserName());
		loginUserDTO.setName(super.getName());
		loginUserDTO.setDeptId(Long.parseLong(super.getDeptId()));
		
		return loginUserDTO;
	}
    
	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
    @GetMapping("read/{id}")
	R get(@PathVariable("id") Long id ){
		UserDTO userDTO = UserConvert.MAPPER.do2dto(userService.get(id));
    	return R.ok().put("data",userDTO);
	}

	/**
	 * 分页查询用户
	 * @param params
	 * @return
	 */
	@Log("获取用户列表")
    @GetMapping("/listByPage")
	@ApiOperation(value = "以分页方式列出有权查看的用户清单", notes = "除了必要查询条件外，需要额外提供每页显示行数，当前需要的页数")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "跳转到的页数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页展示的记录数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "name", value = "搜索关键字", dataType = "String", required = false, paramType = "query"),
        
})
    R listByPage(@RequestParam @ApiParam(hidden =true)Map<String, Object> params) {
        Query query = new Query(params);
        List<UserDTO> userDTOS = UserConvert.MAPPER.dos2dtos((userService.list(query)));
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(userDTOS, total);
        return R.ok().put("page",pageUtil);
    }

	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	@PostMapping("/create")
    R save(@RequestBody UserDO user) {
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	user.setGmtCreate(d);
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		return R.operate(userService.save(user) > 0);
	}

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@PutMapping("/update")
	R update(@RequestBody UserDO user) {
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	user.setGmtModified(d);
		return R.operate(userService.update(user) > 0);
	}

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	@PutMapping("/chpasswd")
	R chpasswd(@RequestBody LoginDTO user) {
        String username = user.getUsername();
        String password = user.getPwd();
        String newpwd = user.getNewPwd();

        //通过验证当前密码 判断是否本人修改
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
        
        // 设置新密码
        UserDO u=new UserDO();
        password = MD5Utils.encrypt(username, newpwd);
        u.setUserId( Long.parseLong(user.getUserId()));
        u.setPassword(password);
		return R.operate(userService.update(u) > 0);
	}

	@PutMapping("/resetpasswd")
	R resetpasswd(@RequestBody LoginDTO user) {
        String username = user.getUsername();
        String newpwd = user.getNewPwd();
        // 设置新密码
        UserDO u=new UserDO();
        String password = MD5Utils.encrypt(username, newpwd);
        u.setUserId( Long.parseLong(user.getUserId()));
        u.setPassword(password);
		return R.operate(userService.update(u) > 0);
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete")
	R remove( Long id) {
		return R.operate (userService.remove(id) > 0);
	}

	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exits")
	@ResponseBody
	boolean exits(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exits(params);
	}
}
