package com.softcloud.grid.common.dto;
    
import com.softcloud.grid.common.utils.*;
import com.softcloud.grid.common.constants.Permission;

public class UserDeptDTO {
	private Long id;
	private Long  userId;
	private Long deptId;
	private String  permission;
	private int transmit;
    // 用户名，用户登录用的account
    private String username;

	//部门名称
	private String name;
	
    //读权限
    private boolean readPermission ;  
    //创新权限
    private boolean addPermission ;
    //修改权限
    private boolean updatePermission ;
    //删除权限
    private boolean deletePermission ;
    //执行管理命令权限
    private boolean admPermission ;
    //执行普通命令权限
    private boolean otherCMDPermission ;
	
	
	

	public boolean isReadPermission() {
		return readPermission;
	}

	public boolean isAddPermission() {
		return addPermission;
	}

	public boolean isUpdatePermission() {
		return updatePermission;
	}

	public boolean isDeletePermission() {
		return deletePermission;
	}

	public boolean isAdmPermission() {
		return admPermission;
	}

	public boolean isOtherCMDPermission() {
		return otherCMDPermission;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPermission() {
		return PermissionBit.binStrToInt(permission);
	}
	public void setPermission(int p) {
		this.permission = PermissionBit.intToBinStr(p);	
		
		this.readPermission =PermissionBit.havePermission(Permission.PERMISSION_8READ,p);

		this.addPermission =PermissionBit.havePermission(Permission.PERMISSION_7CREATE ,p);

		this.updatePermission =PermissionBit.havePermission(Permission.PERMISSION_6UPDATE ,p);

		this.deletePermission =PermissionBit.havePermission(Permission.PERMISSION_5DELETE ,p);

		this.admPermission =PermissionBit.havePermission(Permission.PERMISSION_4ADM_CMD ,p);

		this.otherCMDPermission =PermissionBit.havePermission(Permission.PERMISSION_3OTH_CMD ,p);
	}
	public int getTransmit() {
		return transmit;
	}
	public void setTransmit(int transmit) {
		this.transmit = transmit;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	@Override
	public String toString() {
		return "UserDeptDO{" +
				"id=" + id +
				", userId=" + userId +
				", deptId=" + deptId +
				", permission=" + permission +
				", transmit=" + transmit +
				'}';
	}
}
