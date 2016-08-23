package com.matry.vertx.spring;

import com.matry.vertx.annotation.HttpRequestConsumer;
import com.matry.vertx.network.MatryUrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class HttpRequestProcessor implements BeanPostProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MatryUrlConfig videoBuyUrlConfig;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Method[] methods = o.getClass().getDeclaredMethods();

        if (null != methods) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(HttpRequestConsumer.class)) {
                    HttpRequestConsumer httpRequestConsumer = method.getAnnotation(HttpRequestConsumer.class);

                    try {
                        MethodType mt = MethodType.methodType(void.class);
                        MethodHandles.Lookup l = MethodHandles.lookup();
                        MethodHandle mh = l.findVirtual(o.getClass(), method.getName(), mt);
                        mh.invoke(o);
                        videoBuyUrlConfig.putEvent(httpRequestConsumer.path(), httpRequestConsumer.type());
                    } catch (Throwable e) {
                        logger.error("Object:{} HttpUrlAnnotation Method:{} Must Not Params.", o.getClass(), method.getName());
                        throw new RuntimeException("HttpUrlAnnotation Method Must Not Params.");
                    }

                    String eventName = o.getClass().getSimpleName() + "." + method.getName();
                    videoBuyUrlConfig.putEventName(httpRequestConsumer.path(), eventName);
                }
            }
        }

        return o;
    }
}
