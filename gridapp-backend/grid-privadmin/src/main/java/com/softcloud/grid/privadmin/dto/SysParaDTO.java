package com.softcloud.grid.privadmin.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 部门管理
 * 
 * @author fdl
 * @email 
 * @date 2019-04-18 8:28:36
 */
public class SysParaDTO implements Serializable {
	private static final long serialVersionUID = 198103020736L;
	
	//
	private Long paraId;
	//参数名称
	private String paraName;
	//参数值，默认都是字符串存储
	private String value;

    //对参数类型、参数规则的补充说明
	private String typeDesc;
	//用户显示顺序排序
	private Integer orderNum;
	//备注
	private String remark;
	

	
	public Long getParaId() {
		return paraId;
	}



	public void setParaId(Long paraId) {
		this.paraId = paraId;
	}



	public String getParaName() {
		return paraName;
	}



	public void setParaName(String paraName) {
		this.paraName = paraName;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}





	public String getTypeDesc() {
		return typeDesc;
	}



	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}



	public Integer getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "DeptDO{" +
				"paraId=" + paraId +
				", paraName='" + paraName + '\'' +
				", orderNum=" + orderNum +
				", value=" + value +
				'}';
	}
}
