package com.matry.vertx.handler;

import com.matry.vertx.annotation.HttpRequestConsumer;
import com.matry.vertx.protocol.user.QueryUserInfoReq;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/24  16:02
 */
@Service
public class UserHandler {

    public static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    protected EventBus eventBus;
    @Autowired
    protected DeliveryOptions deliveryOptions;


    @HttpRequestConsumer(path = "/user/query", type = QueryUserInfoReq.class)
    public void queryUserInfo() {
        eventBus.consumer("UserHandler.queryUserInfo", message -> {
            QueryUserInfoReq req = (QueryUserInfoReq) message.body();
            log.debug("query user info:{}", req);

            eventBus.send("UserService.queryUserInfo", req, deliveryOptions);
        });
    }

}
