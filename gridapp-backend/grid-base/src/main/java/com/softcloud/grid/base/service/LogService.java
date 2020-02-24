package com.softcloud.grid.base.service;


import com.softcloud.grid.common.dto.LogDO;
import com.softcloud.grid.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LogService {
    int save(LogDO logDO);

    List<LogDO> queryList(Query query);

    int count(Query query);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
