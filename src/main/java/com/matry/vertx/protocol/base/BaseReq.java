package com.matry.vertx.protocol.base;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:41
 */
public abstract class BaseReq {

    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "BaseReq{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
