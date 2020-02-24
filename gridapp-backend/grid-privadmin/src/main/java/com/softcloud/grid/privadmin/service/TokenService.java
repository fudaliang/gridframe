package com.softcloud.grid.privadmin.service;

import org.springframework.stereotype.Service;

/**
 * @author study spring cloud
 */
@Service
public interface TokenService {
    /**
     * 根据用户id生成token持久化
     */
    String createToken(Long userId);

    Long getUserIdByToken(String token);

    boolean removeToken(String token);
}
