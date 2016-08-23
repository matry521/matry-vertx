package com.matry.vertx.service;

import com.matry.vertx.annotation.EventConsumer;
import com.matry.vertx.constant.Result;
import com.matry.vertx.protocol.user.QueryUserInfoReq;
import com.matry.vertx.protocol.user.QueryUserInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:47
 */
@Service
public class UserService extends BaseService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @EventConsumer
    public void queryUserInfo() {
        eventBus.consumer("UserService.queryUserInfo", message -> {
            QueryUserInfoReq req = (QueryUserInfoReq) message.body();
            logger.debug("queryUserInfo:{}", req);

            QueryUserInfoResp resp = new QueryUserInfoResp();
            resp.setResult(Result.OK);
            resp.setMsg(Result.getErrorMsg(Result.OK));
            //消息回复
            message.reply(resp, deliveryOptions);
        });
    }

}
