package com.matry.vertx;

import com.matry.vertx.protocol.user.QueryUserInfoReq;
import com.matry.vertx.util.JsonUtil;
import org.junit.Test;

import java.util.UUID;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  14:49
 */
public class UserTest extends BaseTest {

    @Test
    public void queryUserInfo() throws InterruptedException {
        QueryUserInfoReq req = new QueryUserInfoReq();
        req.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        req.setUserId(123456l);
        req.setUserName("test");

        String json = JsonUtil.object2Json(req);

        httpRequest("/user/query", json);

        Thread.sleep(10000);
    }
}
