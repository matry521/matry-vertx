package com.matry.vertx.protocol.base;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:56
 */
public abstract class BaseResp {

    @NotNull
    @Min(0)
    private int result;     //返回状态码
    private String msg;     //返回信息


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
