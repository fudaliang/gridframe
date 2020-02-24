package com.softcloud.grid.privadmin.service;


import com.softcloud.grid.privadmin.domain.SysParaDO;
import com.softcloud.grid.privadmin.dto.SysParaDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface SysParaService {
	SysParaDO get(Long paraId);

	List<SysParaDO> list(Map<String, Object> map);

	List<SysParaDO> listByParaType(String paraType);

	List<SysParaDO> listParaType();

	int count(Map<String, Object> map);

	int save(SysParaDO syspara);

	int update(SysParaDO syspara);

	
}
