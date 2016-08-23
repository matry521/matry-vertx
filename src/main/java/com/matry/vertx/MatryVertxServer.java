package com.matry.vertx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/24  15:58
 */
public class MatryVertxServer {

    private final static Logger logger = LoggerFactory.getLogger(MatryVertxServer.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml");
        context.registerShutdownHook();

        logger.info("MatryVertxServer start...");
    }
}
