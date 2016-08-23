package com.matry.vertx.service;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  10:49
 */
public class BaseService {
    @Autowired
    protected EventBus eventBus;
    @Autowired
    protected DeliveryOptions deliveryOptions;
}
