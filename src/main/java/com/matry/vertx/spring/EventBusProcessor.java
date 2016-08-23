package com.matry.vertx.spring;

import com.matry.vertx.codec.ObjectCodec;
import io.vertx.core.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class EventBusProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof EventBus) {
            EventBus eventBus = (EventBus) o;
            eventBus.registerCodec(new ObjectCodec());
            eventBus.isMetricsEnabled();
        }

        return o;
    }
}
