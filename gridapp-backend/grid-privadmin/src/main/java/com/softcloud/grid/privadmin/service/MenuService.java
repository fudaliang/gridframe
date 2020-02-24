package com.softcloud.grid.privadmin.service;

import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.common.dto.UserMenuPermissionDTO;
import com.softcloud.grid.privadmin.domain.MenuDO;
import com.softcloud.grid.common.dto.RouterDTO;
import com.softcloud.grid.common.utils.R;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface MenuService {
	Tree<MenuDO> getSysMenuTree(Long id);

	/**
	 * 登录用户的权限
	 * @author study spring cloud
	 * @param userId
	 * @return
	 */
	List<MenuDO> userMenus(Long userId);
    // every one can accesss url
	List<MenuDO> publicMenus();

	List<Tree<MenuDO>> listMenuTree(Long id);

	Tree<MenuDO> getTree();

	Tree<MenuDO> getTree(Long id);

	@CacheEvict(value = "permission",key = "#userId")
	boolean clearCache(Long userId);

	List<MenuDO> list(Map<String, Object> params);

	int remove(Long id);

	int save(MenuDO menu);

	int update(MenuDO menu);

	MenuDO get(Long id);


	/**
	 * 获取角色下的权限所有id
	 * @param roleId
	 * @return
	 */
	List<Long> MenuIdsByRoleId(Long roleId);

	/**
	 * 用户的路由
	 * @return
	 */
	List<RouterDTO> RouterDTOsByUserId(Long userId);
	/**
	 * 用户权限
	 */
	List<UserMenuPermissionDTO> PermsByUserId(Long userId);
}
