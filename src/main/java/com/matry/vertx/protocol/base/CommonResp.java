package com.matry.vertx.protocol.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CommonResp {
    private int result;
    private String msg;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .append("msg", msg)
                .toString();
    }
}
