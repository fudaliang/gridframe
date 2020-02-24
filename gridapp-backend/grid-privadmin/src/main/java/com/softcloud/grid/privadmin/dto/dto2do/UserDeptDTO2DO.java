/**
 * 
 */
/**
 * @author fdl
 *
 */
package com.softcloud.grid.privadmin.dto.dto2do;


import com.softcloud.grid.privadmin.domain.UserDeptDO;
import com.softcloud.grid.common.dto.UserDeptDTO;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface UserDeptDTO2DO {
    UserDeptDTO2DO MAPPER = Mappers.getMapper(UserDeptDTO2DO.class);

    public UserDeptDO dto2do(UserDeptDTO userDept);

    public List<UserDeptDO> dtos2dos(List<UserDeptDTO> list);
}
