package com.softcloud.grid.privadmin.utils;

import com.softcloud.grid.privadmin.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author study spring cloud
 * @version V1.0
 */
public class TokenUtils {
    @Autowired
    TokenDao tokenDao;
    String getCurrent(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
