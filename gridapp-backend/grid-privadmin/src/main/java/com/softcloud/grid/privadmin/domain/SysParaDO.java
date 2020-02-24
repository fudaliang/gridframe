package com.softcloud.grid.privadmin.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 部门管理
 * 
 * @author fdl
 * @email 121619233@qq.com
 * @date 2019-04-18 8:28:36
 */
public class SysParaDO implements Serializable {
	private static final long serialVersionUID = 198103020736L;
	
	//
	private Long paraId;
	//参数名称
	private String paraName;
	//参数值，默认都是字符串存储
	private String value;
    //参数类型，每种类型参数值必须唯一
	private String paraType;
    //对参数类型、参数规则的补充说明
	private String typeDesc;
	//用户显示顺序排序
	private Integer orderNum;
	//上级参数ID，如有用于显示多级参数
	private Long parentId;
	
    // 创建用户id
    private Long userIdCreate;
    // 创建时间
    private Date gmtCreate;
    // 创建用户id
    private Long userIdModified;
    // 修改时间
    private Date gmtModified;
	//备注
	private String remark;
	
	//是否删除  0：已删除  1：正常
	private Integer delFlag;


	public String getTypeDesc() {
		return typeDesc;
	}




	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}




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




	public String getParaType() {
		return paraType;
	}




	public void setParaType(String paraType) {
		this.paraType = paraType;
	}



	public Integer getOrderNum() {
		return orderNum;
	}




	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}




	public Long getParentId() {
		return parentId;
	}




	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}




	public Long getUserIdCreate() {
		return userIdCreate;
	}




	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}




	public Date getGmtCreate() {
		return gmtCreate;
	}




	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}




	public Long getUserIdModified() {
		return userIdModified;
	}




	public void setUserIdModified(Long userIdModified) {
		this.userIdModified = userIdModified;
	}




	public Date getGmtModified() {
		return gmtModified;
	}




	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public Integer getDelFlag() {
		return delFlag;
	}




	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		return "DeptDO{" +
				"paraId=" + paraId +
				", parentId=" + parentId +
				", paraName='" + paraName + '\'' +
				", orderNum=" + orderNum +
				", delFlag=" + delFlag +
				'}';
	}
}
