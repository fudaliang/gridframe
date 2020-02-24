package com.softcloud.grid.privadmin.service.impl;



import com.softcloud.grid.privadmin.dto.SysParaDTO;
import com.softcloud.grid.privadmin.service.SysParaService;
import com.softcloud.grid.privadmin.dao.SysParaDao;
import com.softcloud.grid.privadmin.domain.SysParaDO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author study spring cloud
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysParaServiceImpl implements SysParaService {
	@Autowired
	SysParaDao paraMapper;


	private static final Logger logger = LoggerFactory.getLogger(SysParaService.class);

	@Override
	public SysParaDO get(Long paraId) {
		SysParaDO para = paraMapper.get(paraId);
		return para;
	}

	@Override
	public List<SysParaDO> list(Map<String, Object> map) {
		return paraMapper.list(map);
	}
	
	@Override
	public List<SysParaDO> listByParaType(String paraType) {
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("paraType", paraType);
		map.put("delFlag","1");
		return paraMapper.listByParaType(map);
	}	
	@Override
	public List<SysParaDO> listParaType() {
		return paraMapper.listParaType();
	}
	@Override
	public int count(Map<String, Object> map) {
		return paraMapper.count(map);
	}
	@Override
	public int save(SysParaDO para) {
		int count = paraMapper.save(para);
		return count;
	}

	@Override
	public int update(SysParaDO para) {
		int r = paraMapper.update(para);
		
		return r;
	}


}
