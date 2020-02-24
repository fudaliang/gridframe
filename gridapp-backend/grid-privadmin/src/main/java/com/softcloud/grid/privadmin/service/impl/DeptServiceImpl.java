package com.softcloud.grid.privadmin.service.impl;

import com.softcloud.grid.privadmin.domain.Tree;
import com.softcloud.grid.privadmin.utils.BuildTree;
import com.softcloud.grid.privadmin.dao.DeptDao;
import com.softcloud.grid.privadmin.domain.DeptDO;
import com.softcloud.grid.privadmin.service.DeptService;
import com.softcloud.grid.privadmin.dao.UserDeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao sysDeptMapper;
	
	@Autowired
	private UserDeptDao sysUserDeptMapper;
	

	@Override
	public DeptDO get(Long deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<DeptDO> listParentDept(Map<String, Object> map){
		return sysDeptMapper.listParentDept(map);
	}

	@Override
	public List<DeptDO> listChildDept(Map<String, Object> map){
		return sysDeptMapper.listChildDept(map);
	}
	@Override
	public List<DeptDO> list(Map<String, Object> map){
		return sysDeptMapper.list(map);
	}
	
	@Override
	public List<DeptDO>  batchList(Long[] deptIds){
		return sysDeptMapper.batchList(deptIds);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int countChild(Map<String, Object> map) {
		return sysDeptMapper.countChild(map);
	}

	@Override
	public int countParent(Map<String, Object> map) {
		return sysDeptMapper.countParent(map);
	}
	
	@Override
	public int save(DeptDO sysDept){
		return sysDeptMapper.save(sysDept);
	}

	@Override
	public int update(DeptDO sysDept){
		return sysDeptMapper.update(sysDept);
	}

	@Transactional
	@Override
	public int remove(Long deptId){
		int count=sysDeptMapper.remove(deptId);
		sysUserDeptMapper.removeByDeptId(deptId);
		return count;
		
	}

	@Transactional
	@Override
	public int batchRemove(Long[] deptIds){
		int count = sysDeptMapper.batchRemove(deptIds);
		sysUserDeptMapper.batchRemove(deptIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree(Map<String,Object> map) {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();

		List<DeptDO> sysDepts = sysDeptMapper.listChildDept(map);
		for (DeptDO sysDept : sysDepts) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkDeptHasUser(Long deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		return result==0?true:false;
	}

}
