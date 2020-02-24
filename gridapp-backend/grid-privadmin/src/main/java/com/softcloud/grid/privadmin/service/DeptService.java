package com.softcloud.grid.privadmin.service;

import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.privadmin.domain.DeptDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	DeptDO get(Long deptId);
	
	List<DeptDO> listParentDept(Map<String, Object> map);
	
	List<DeptDO> listChildDept(Map<String, Object> map);
	
	List<DeptDO> list(Map<String, Object> map);
	
    List<DeptDO>  batchList(Long[] deptIds);
	
	int countChild(Map<String, Object> map);
	
	int countParent(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO sysDept);
	
	int update(DeptDO sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<DeptDO> getTree(Map<String,Object> paras);
	
	boolean checkDeptHasUser(Long deptId);
	
}
