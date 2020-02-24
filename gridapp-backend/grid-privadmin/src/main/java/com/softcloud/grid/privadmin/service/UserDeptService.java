package com.softcloud.grid.privadmin.service;

import com.softcloud.grid.common.dto.UserDeptDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserDeptService {
	
	UserDeptDTO get(Long id);

	List<UserDeptDTO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDeptDTO userDept);

	int batchSave(List<UserDeptDTO> list);
	
	int update(UserDeptDTO userDept);
	
	int remove(Long id);

	int batchRemove(Long[] ids);
	
	int removeByUserId(Long userId);
	
	int removeByDeptId(Long deptId);
     // list current login user have access permission depts
	List<UserDeptDTO> userDepts(Long userId);
}
