package com.softcloud.grid.common.dto;

public class LoginUserDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户登录名
     */
    private String username;
    /**
     * 用户真实姓名
     */
    private String name;
    
    //用户所在部门
    private Long deptId;

    public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
