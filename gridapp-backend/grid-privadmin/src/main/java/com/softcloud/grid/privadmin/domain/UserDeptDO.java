package com.softcloud.grid.privadmin.domain;

public class UserDeptDO  {
	private Long id;
	private Long  userId;
	private Long deptId;
	private int  permission;
	private int transmit;
	
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
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
