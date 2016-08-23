package com.matry.vertx.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:59
 */
public class Result {


    public static final int OK = 0;
    public static final int ERR_REQUEST = 1;
    public static final int ERR_PARAMS = 2;

    private static Map<Integer, String> errorMsgs = new HashMap<>();

    static {
        errorMsgs.put(OK, "成功返回.");
        errorMsgs.put(ERR_REQUEST, "请求地址不存在,请稍后重试.");
        errorMsgs.put(ERR_PARAMS, "参数错误,请稍后重试.");
    }

    public static String getErrorMsg(int key) {
        return errorMsgs.get(key);
    }
}
