package com.softcloud.grid.privadmin.dto.do2dto;


import com.softcloud.grid.privadmin.domain.SysParaDO;
import com.softcloud.grid.privadmin.dto.SysParaTypeDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface SysParaTypeConvert {
    SysParaTypeConvert MAPPER = Mappers.getMapper(SysParaTypeConvert.class);

    public SysParaTypeDTO do2dto(SysParaDO paratype);

    public List<SysParaTypeDTO> dos2dtos(List<SysParaDO> list);
}
