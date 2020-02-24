package com.softcloud.grid.privadmin.controller;

import com.softcloud.grid.privadmin.domain.SysParaDO;
import com.softcloud.grid.privadmin.dto.SysParaDTO;
import com.softcloud.grid.privadmin.dto.SysParaTypeDTO;
import com.softcloud.grid.privadmin.dto.do2dto.SysParaConvert;
import com.softcloud.grid.privadmin.dto.do2dto.SysParaTypeConvert;
import com.softcloud.grid.privadmin.service.SysParaService;
import com.softcloud.grid.common.context.FilterContextHandler;
import com.softcloud.grid.common.utils.PageUtils;
import com.softcloud.grid.common.utils.Query;
import com.softcloud.grid.common.utils.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author fdl
 *   系统参数管理
 */
@Api(tags = {"系统参数管理"} )
@RequestMapping("/sysPara")
@RestController
public class SysParaController {
    @Autowired
    SysParaService SysParaService;

	@ApiOperation(value = "以分页方式列出有权查看的参数清单", notes = "除了必要查询条件外，需要额外提供每页显示行数，当前需要的页数")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "跳转到的页数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页展示的记录数", dataType = "String", required = true, paramType = "query"),
        @ApiImplicitParam(name = "name", value = "搜索关键字", dataType = "String", required = false, paramType = "query"),
        
})
    @GetMapping("/read")
    PageUtils list(@RequestParam @ApiParam(hidden =true)Map<String, Object> params) {
        Query query = new Query(params);
        List<SysParaDO> paraDOS = SysParaService.list(query);
        int total = SysParaService.count(query);
        PageUtils pageUtil = new PageUtils(paraDOS, total);
        return pageUtil;
    }

    //@GetMapping("/listByParaType/{paraType}")
    @GetMapping("/listByParaType")
    List<SysParaDTO> listByParaType(String paraType){
    	List<SysParaDO> paraDOS= SysParaService.listByParaType(paraType);
    	return SysParaConvert.MAPPER.dos2dtos(paraDOS);
    }
    @GetMapping("/listParaType")
    List<SysParaTypeDTO> listParaType(){
    	List<SysParaDO> paraDOS =  SysParaService.listParaType();
        return SysParaTypeConvert.MAPPER.dos2dtos(paraDOS);
    }

    @PostMapping("/create")
    R save(@RequestBody SysParaDO SysParaDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	SysParaDO.setGmtCreate(d);
    	SysParaDO.setUserIdCreate(Long.parseLong(FilterContextHandler.getUserID()));
    	SysParaDO.setUserIdModified(Long.parseLong(FilterContextHandler.getUserID()));
        if(SysParaService.save(SysParaDO)>0){
            return R.ok();
        }
        return R.error();
    }

    @PutMapping("/update")
    R update(@RequestBody SysParaDO SysParaDO){
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	SysParaDO.setGmtModified(d);
    	SysParaDO.setUserIdModified(Long.parseLong(FilterContextHandler.getUserID()));
    	
        if(SysParaService.update(SysParaDO)>0){
            return R.ok();
        }
        return R.error();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    R delete(Long id){
    	// 系统参数，只进行逻辑删除
		if (id == null ) {
			return R.error("sys para ID is null");
		}
		SysParaDO SysParaDO=new SysParaDO();
    	Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	SysParaDO.setGmtModified(d);
    	SysParaDO.setUserIdModified(Long.parseLong(FilterContextHandler.getUserID()));
    	SysParaDO.setDelFlag(0);
    	SysParaDO.setParaId(id);
        if(SysParaService.update(SysParaDO)>0){
            return R.ok();
        }
        return R.error();
 
    }

}
