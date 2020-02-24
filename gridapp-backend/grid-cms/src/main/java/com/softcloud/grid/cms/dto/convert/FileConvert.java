package com.softcloud.grid.cms.dto.convert;


import com.softcloud.grid.cms.domain.FileDO;
import com.softcloud.grid.cms.dto.FileDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface FileConvert {
    FileConvert MAPPER = Mappers.getMapper(FileConvert.class);

    public FileDTO do2dto(FileDO person);

    public List<FileDTO> dos2dtos(List<FileDO> list);
}