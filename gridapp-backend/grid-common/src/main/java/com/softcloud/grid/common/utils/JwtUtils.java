package com.softcloud.grid.common.utils;

import com.softcloud.grid.common.constants.CommonConstants;
import com.softcloud.grid.common.dto.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author study spring cloud
 * @version V1.0
 */
public class JwtUtils {
    public static String generateToken(UserToken userToken, int expire) throws Exception {
        String token = Jwts.builder()
                .setSubject(userToken.getUsername())
                .claim(CommonConstants.CONTEXT_USER_ID, userToken.getUserId())
                .claim(CommonConstants.CONTEXT_NAME, userToken.getName())
                .claim(CommonConstants.CONTEXT_DEPTID, userToken.getDeptId())
                .claim(CommonConstants.RENEWAL_TIME,new Date(System.currentTimeMillis()+expire/2))
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY)
                .compact();
        return token;
    }


    public static UserToken getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(token)
                .getBody();
        return new UserToken(claims.getSubject(), claims.get(CommonConstants.CONTEXT_USER_ID).toString(),claims.get(CommonConstants.CONTEXT_NAME).toString(),Long.parseLong(claims.get(CommonConstants.CONTEXT_DEPTID).toString()));
    }
}
