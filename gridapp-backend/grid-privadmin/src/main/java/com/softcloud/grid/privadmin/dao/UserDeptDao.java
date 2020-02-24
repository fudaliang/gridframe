package com.softcloud.grid.privadmin.dao;

import com.softcloud.grid.privadmin.domain.UserDeptDO;
import com.softcloud.grid.common.dto.UserDeptDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
*
 */
@Mapper
public interface UserDeptDao {

	UserDeptDO get(Long id);
	
	List<UserDeptDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	UserDeptDO listDeptByUserId(Long UserId);
	
	List<Long> listUserIdByDeptId(Long DeptId);
	
	int save(UserDeptDO userDept);
	
	int batchSave(List<UserDeptDO> list);
	
	int update(UserDeptDO userDept);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	int removeByUserId(Long UserId);
	
	int batchRemoveByUserId(Long[] UserId);

	int removeByDeptId(Long DeptId);
	
	int removeByUserIdDeptID(Long UserId,Long DeptId);
	
}
