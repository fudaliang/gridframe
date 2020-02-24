package com.softcloud.grid.privadmin.service.impl;

import com.softcloud.grid.privadmin.utils.*;
import com.softcloud.grid.privadmin.dao.DeptDao;
import com.softcloud.grid.privadmin.dao.UserDao;
import com.softcloud.grid.privadmin.dao.UserDeptDao;
import com.softcloud.grid.privadmin.domain.DeptDO;
import com.softcloud.grid.privadmin.domain.UserDO;
import com.softcloud.grid.privadmin.domain.UserDeptDO;
import com.softcloud.grid.common.dto.UserDeptDTO;
import com.softcloud.grid.privadmin.service.UserDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softcloud.grid.privadmin.dto.do2dto.UserDeptConvert;
import com.softcloud.grid.privadmin.dto.dto2do.UserDeptDTO2DO;

import java.util.*;



@Transactional(rollbackFor = Exception.class)
@Service
public class UserDeptServiceImpl implements UserDeptService {
	@Autowired
	UserDeptDao userDeptMapper;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	UserDao userMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserDeptService.class);

	@Override
	public UserDeptDTO get(Long id) {
		UserDeptDO userDept = userDeptMapper.get(id);
		return UserDeptConvert.MAPPER.do2dto(userDept);
	}

	@Override
	public List<UserDeptDTO> list(Map<String, Object> map) {
		// 组织查询过滤条件
		String queryDeptId =String.valueOf(map.get("deptId"));
		String queryUserId = String.valueOf(map.get("userId"));

		if ( queryDeptId == null || "".equals(queryDeptId)) {
	        map.remove("deptId");
		}
	    if ( queryUserId == null ||  "".equals(queryUserId)) {
			map.remove("userId");
		} 

		List<UserDeptDO> list = userDeptMapper.list(map);
		// 组包查询 部门名称和用户登录账号
		Long deptIDs[] = new Long[list.size()];
		Long userIDs[] = new Long[list.size()];
		UserDeptDO tmpUserDeptDo = new UserDeptDO();
		for (int i = 0; i < list.size(); i++) {
			tmpUserDeptDo = list.get(i);
			deptIDs[i] = tmpUserDeptDo.getDeptId();
			userIDs[i] = tmpUserDeptDo.getUserId();
		}

		List<UserDO> userList = userMapper.batchList(userIDs);
		List<DeptDO> deptList = deptMapper.batchList(deptIDs);
		List<UserDeptDTO> userDeptList = new ArrayList<>();
		UserDeptDTO tmpUserDeptDTO = new UserDeptDTO();

		for (int i = 0; i < list.size(); i++) {
			tmpUserDeptDo = list.get(i);
			tmpUserDeptDTO = UserDeptConvert.MAPPER.do2dto(tmpUserDeptDo);
			Long userId = tmpUserDeptDo.getUserId();
			Long deptId = tmpUserDeptDo.getDeptId();
			for (DeptDO dept : deptList) {
				if (deptId.equals(dept.getDeptId())) {
					tmpUserDeptDTO.setName(dept.getName());
					break;
				}
			}

			for (UserDO user : userList) {
				if (userId.equals(user.getUserId())) {
					tmpUserDeptDTO.setUsername(user.getName());
					break;
				}
			}
			userDeptList.add(tmpUserDeptDTO);

		}

		return userDeptList;
	}

	@Override
	public int count(Map<String, Object> map) {
		return userDeptMapper.count(map);
	}

	@Override
	public int save(UserDeptDTO userDept) {
		UserDeptDO R = UserDeptDTO2DO.MAPPER.dto2do(userDept);
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("userId", R.getUserId());
		map.put("deptId",R.getDeptId());
		if (userDeptMapper.count(map) > 0 ) {
			return userDeptMapper.update(R);
		} else {
			return userDeptMapper.save(R);
		}
		
		
	}

	@Override
	public int update(UserDeptDTO userDept) {
		UserDeptDO R = UserDeptDTO2DO.MAPPER.dto2do(userDept);
		return userDeptMapper.update(R);
	}

	@Override
	public int remove(Long id) {
		return userDeptMapper.remove(id);
	}

	@Override
	public int batchSave(List<UserDeptDTO> list) {
		List<UserDeptDO> R = UserDeptDTO2DO.MAPPER.dtos2dos(list);
		return userDeptMapper.batchSave(R);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return userDeptMapper.batchRemove(ids);
	}

	@Override
	public int removeByUserId(Long userId) {
		return userDeptMapper.removeByUserId(userId);
	}

	@Override
	public int removeByDeptId(Long deptId) {
		return userDeptMapper.removeByDeptId(deptId);
	}
	@Override
	public  List<UserDeptDTO> userDepts(Long userId) {
		Map<String, Object> map= new HashMap();
		map.put("userId", userId);
		List<UserDeptDO>  list= userDeptMapper.list(map);
	    return  UserDeptConvert.MAPPER.dos2dtos(list);
	}
}
