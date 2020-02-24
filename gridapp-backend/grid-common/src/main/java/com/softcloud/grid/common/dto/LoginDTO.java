package com.softcloud.grid.common.dto;

import javax.validation.constraints.NotNull;

public class LoginDTO {
    @NotNull
    private String username;
    @NotNull
    private String pwd;
    
    private String newPwd;
 
    private String cookiesGenerateDate;

    private String userId;
    
    private String deptId;



	public String getCookiesGenerateDate() {
		return cookiesGenerateDate;
	}

	public void setCookiesGenerateDate(String cookiesGenerateDate) {
		this.cookiesGenerateDate = cookiesGenerateDate;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String password) {
        this.pwd = password;
    }
}
