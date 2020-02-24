package com.softcloud.grid.privadmin.dao;

import com.softcloud.grid.privadmin.domain.SysParaDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author fdl
 * @email121619233@qq.comm
 * @date 2019-04-18 15:24:47
 */
@Mapper
public interface SysParaDao {

	SysParaDO get(Long paraId);
	
	List<SysParaDO> list(Map<String, Object> map);
	
	List<SysParaDO> listByParaType(Map<String, Object> map);

	List<SysParaDO> listParaType();

	int count(Map<String, Object> map);
	
	int save(SysParaDO syspara);
	
	int update(SysParaDO syspara);
	
}
