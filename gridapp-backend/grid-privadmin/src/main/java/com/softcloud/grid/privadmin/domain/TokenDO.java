package com.softcloud.grid.privadmin.domain;

import java.util.Date;

/**
 * @author study spring cloud
 * @version V1.0
 */
public class TokenDO {
    private static final long serialVersionUID = 198103020736L;

    @Override
    public String toString() {
        return "TokenDO{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", expireTime=" + expireTime +
                ", updateTime=" + updateTime +
                '}';
    }

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
