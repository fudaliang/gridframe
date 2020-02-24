package com.softcloud.grid.common.dto;

public class UserMenuPermissionDTO {

	//
	private String component;
	// sum memu perms by component
	private int componentPerms;
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public int getComponentPerms() {
		return componentPerms;
	}
	public void setComponentPerms(int componentPerms) {
		this.componentPerms = componentPerms;
	}

	
	
}
