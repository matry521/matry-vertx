package com.matry.vertx.spring;

import com.matry.vertx.annotation.EventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class EventProcessor implements BeanPostProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Method[] methods = o.getClass().getDeclaredMethods();

        if (null != methods) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(EventConsumer.class)) {
                    try {
                        MethodType mt = MethodType.methodType(void.class);
                        MethodHandles.Lookup l = MethodHandles.lookup();
                        MethodHandle mh = l.findVirtual(o.getClass(), method.getName(), mt);
                        mh.invoke(o);
                    } catch (Throwable e) {
                        logger.error("Object:{} EventAnnotation Method:{} Must Not Params.", o.getClass(), method.getName());
                        throw new RuntimeException("EventAnnotation Method Must Not Params.");
                    }
                }
            }
        }

        return o;
    }
}
