package com.matry.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @描述:
 * @作者: 马天元
 * @时间: 2016/5/25  14:36
 */
public class BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public void httpRequest(String requestUrl, String body) {
        Vertx vertx = Vertx.vertx();
        HttpClient httpClient = vertx.createHttpClient();

        httpClient.post(9091, "127.0.0.1", requestUrl, result -> {
            Buffer totalBuffer = Buffer.buffer();

            result.handler(buffer -> {
                logger.debug("Receive a part of the response body:{}", buffer.length());
                totalBuffer.appendBuffer(buffer);
            });

            result.endHandler(buffer -> {
                logger.debug("Total response body length is:{}", totalBuffer.length());
                logger.debug("resp content:{}", new String(totalBuffer.getBytes()));
            });
        }).end(body);

    }
}
