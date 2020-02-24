package com.softcloud.grid.privadmin.dto.do2dto;


import com.softcloud.grid.privadmin.domain.SysParaDO;
import com.softcloud.grid.privadmin.dto.SysParaDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface SysParaConvert {
    SysParaConvert MAPPER = Mappers.getMapper(SysParaConvert.class);

    public SysParaDTO do2dto(SysParaDO paratype);

    public List<SysParaDTO> dos2dtos(List<SysParaDO> list);
}
