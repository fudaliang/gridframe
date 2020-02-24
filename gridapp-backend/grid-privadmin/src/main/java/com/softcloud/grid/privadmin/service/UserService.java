package com.softcloud.grid.privadmin.service;

import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.privadmin.domain.DeptDO;
import com.softcloud.grid.privadmin.domain.UserDO;
import com.softcloud.grid.privadmin.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {
	UserDO get(Long id);

	List<UserDO> list(Map<String, Object> map);
	
	List<UserDO> batchList(Long[] userIds);
	
	int count(Map<String, Object> map);

	int save(UserDO user);

	int update(UserDO user);

	int remove(Long userId);

	int batchremove(Long[] userIds);

	boolean exits(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO, UserDO userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<DeptDO> getTree();

	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	int updatePersonal(UserDO userDO);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
