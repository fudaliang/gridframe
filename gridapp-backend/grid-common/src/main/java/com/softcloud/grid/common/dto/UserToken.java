package com.softcloud.grid.common.dto;

import java.io.Serializable;

/**
 * @author study spring cloud
 * @version V1.0
 */
public class UserToken implements Serializable{
    private static final long serialVersionUID = 198103020736L;

    public UserToken(String username, String userId, String name,Long deptId) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.deptId= deptId;
    }

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

    @Override
    public String toString() {
        return "UserToken{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
