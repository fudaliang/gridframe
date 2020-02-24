package com.softcloud.grid.privadmin.dto.do2dto;


import com.softcloud.grid.privadmin.domain.UserDeptDO;
import com.softcloud.grid.common.dto.UserDeptDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface UserDeptConvert {
    UserDeptConvert MAPPER = Mappers.getMapper(UserDeptConvert.class);

    public UserDeptDTO do2dto(UserDeptDO userDept);

    public List<UserDeptDTO> dos2dtos(List<UserDeptDO> list);
}
