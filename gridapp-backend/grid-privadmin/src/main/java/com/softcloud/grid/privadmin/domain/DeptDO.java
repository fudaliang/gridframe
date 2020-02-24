package com.softcloud.grid.privadmin.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 资源组管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public class DeptDO implements Serializable {
	private static final long serialVersionUID = 198103020736L;
	
	//
	private Long deptId;
	//上级资源组ID，一级资源组为0
	private Long parentId;
	//资源组名称
	private String name;
	// 级别
	private String deptLevel;
	//资源组名称
	private String pname;

	//排序
	private Integer orderNum;
	//是否删除  -1：已删除  0：正常
	private Integer delFlag;
	//备注
	private String remark;
	
	private Integer ownerPerms;
	
	private Integer groupPerms;
	
	private Integer otherPerms;
	
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	
	
	public String getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
	public Integer getOwnerPerms() {
		return ownerPerms;
	}
	public void setOwnerPerms(Integer ownerPerms) {
		this.ownerPerms = ownerPerms;
	}
	public Integer getGroupPerms() {
		return groupPerms;
	}
	public void setGroupPerms(Integer groupPerms) {
		this.groupPerms = groupPerms;
	}
	public Integer getOtherPerms() {
		return otherPerms;
	}
	public void setOtherPerms(Integer otherPerms) {
		this.otherPerms = otherPerms;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * 设置：
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 设置：上级资源组ID，一级资源组为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级资源组ID，一级资源组为0
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：资源组名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：资源组名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：是否删除  -1：已删除  0：正常
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：是否删除  -1：已删除  0：正常
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPname() {
		return pname;
	}
	public void setpPname(String pname) {
		this.pname = pname;
	}
	@Override
	public String toString() {
		return "DeptDO{" +
				"deptId=" + deptId +
				", parentId=" + parentId +
				", name='" + name + '\'' +
				", orderNum=" + orderNum +
				", delFlag=" + delFlag +
				'}';
	}
}
