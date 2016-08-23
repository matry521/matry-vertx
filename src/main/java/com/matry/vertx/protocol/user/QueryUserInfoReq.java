package com.matry.vertx.protocol.user;

import com.matry.vertx.protocol.base.BaseReq;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:33
 */
public class QueryUserInfoReq extends BaseReq {

    @NotNull
    private Long userId;
    @Size(min = 1, max = 20)
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return new StringBuilder("QueryUserInfoReq:{")
                .append("userId").append(userId)
                .append("userName").append(userName)
                .append("}").toString();
    }
}
