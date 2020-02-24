package com.softcloud.grid.privadmin.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 部门管理
 * 
 * @author fdl
 * @email 121619233@qq.com
 * @date 2019-04-18 8:28:36
 */
public class SysParaTypeDTO  {

    //参数类型，每种类型参数值必须唯一
	private String paraType;

	//对参数类型、参数规则的补充说明
	private String typeDesc;

    public String getParaType() {
		return paraType;
	}
	public void setParaType(String paraType) {
		this.paraType = paraType;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
