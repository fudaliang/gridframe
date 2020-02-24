package com.softcloud.grid.privadmin.controller;

import org.springframework.web.bind.annotation.RestController;
import com.softcloud.grid.common.context.FilterContextHandler;


@RestController
public class BaseController {
	// current login user info. 

	public String getUserName() {
		return FilterContextHandler.getUsername();
	}

	public String getName() {
		return FilterContextHandler.getName();
	}


	public String getUserID() {
		return FilterContextHandler.getUserID();
	}

	// ID of user's Department 
	public String getDeptId() {
		return FilterContextHandler.getDeptId();
	}

	public String getCurrToken() {
		return FilterContextHandler.getToken();
	}

	
}
