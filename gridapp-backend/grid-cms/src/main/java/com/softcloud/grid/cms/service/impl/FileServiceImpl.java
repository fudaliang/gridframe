package com.softcloud.grid.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.softcloud.grid.cms.dao.FileDao;
import com.softcloud.grid.cms.domain.FileDO;
import com.softcloud.grid.cms.service.FileService;



@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao fileDao;
	
	@Override
	public FileDO get(Long id){
		return fileDao.get(id);
	}
	
	@Override
	public List<FileDO> list(Map<String, Object> map){
		return fileDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return fileDao.count(map);
	}
	
	@Override
	public int save(FileDO file){
		return fileDao.save(file);
	}
	
	@Override
	public int update(FileDO file){
		return fileDao.update(file);
	}
	
	@Override
	public int remove(Long id){
		return fileDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return fileDao.batchRemove(ids);
	}
	
}
