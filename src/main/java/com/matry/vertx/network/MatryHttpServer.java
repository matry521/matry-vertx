package com.matry.vertx.network;


import com.matry.vertx.constant.Result;
import com.matry.vertx.protocol.base.CommonResp;
import com.matry.vertx.util.JsonUtil;
import com.matry.vertx.validation.BeanValidation;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class MatryHttpServer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Vertx vertx;
    @Autowired
    private EventBus eventBus;
    @Qualifier("httpServerOptions")
    @Autowired
    private HttpServerOptions options;
    @Autowired
    private DeliveryOptions deliveryOptions;
    @Autowired
    private MatryUrlConfig videoBuyUrlConfig;

    private HttpServer httpServer;


    @PostConstruct
    public void start() {
        httpServer = vertx.createHttpServer(options);
        httpServer.requestHandler(request -> {
            if (request.method() == HttpMethod.POST) {
                if (StringUtils.isEmpty(request.path())){
                    this.returnCommonErrorResp(request,"",Result.ERR_REQUEST);
                    return;
                }
                logger.debug("path:{},", request.path());

                Buffer totalBuffer = Buffer.buffer();

                request.handler(buffer -> {
                    logger.debug("request handler:{}", buffer);
                    totalBuffer.appendBuffer(buffer);
                });

                request.endHandler(v -> {
                    //转换成json对象
                    logger.debug("base request endHandler:{}", totalBuffer);
                    String json = totalBuffer.toString();

                    String fullPath = request.path();
                    String encodeType = "";
                    String path = fullPath;

                    String eventName = videoBuyUrlConfig.getEventName(path);

                    if (StringUtils.isEmpty(eventName)) {
                        //返回公共的错误代码
                        this.returnCommonErrorResp(request, encodeType, Result.ERR_REQUEST);
                        return;
                    }

                    Object o = JsonUtil.jsonTObject(json, videoBuyUrlConfig.getEvent(path));

                    if (null == o) {
                        //返回公共的错误代码
                        this.returnCommonErrorResp(request, encodeType, Result.ERR_REQUEST);
                        return;
                    }

                    //验证bean
                    String errorMsg = BeanValidation.validate(o);

                    if (null != errorMsg) {
                        CommonResp resp = new CommonResp();
                        resp.setResult(Result.ERR_PARAMS);
                        resp.setMsg(errorMsg);
                        sendRespMsg(request, JsonUtil.object2Json(resp));
                        return;
                    }

                    eventBus.send(eventName, o, deliveryOptions, ar -> {
                        if (ar.succeeded()) {
                            //返回请求给客户端
                            Object reply = ar.result().body();
                            logger.debug("reply:{}", reply);

                            String replyJson = JsonUtil.object2Json(reply);
                            sendRespMsg(request, replyJson);
                            return;
                        }
                    });
                });
            } else if (request.method() == HttpMethod.GET) {
                return;
            }
        });

        httpServer.listen(res -> {
            if (res.succeeded()) {
                logger.debug("MatryHttpServer bind port:{} successful.", options.getPort());
            } else {
                logger.warn("MatryHttpServer bind port:{} failure.", options.getPort());
            }
        });
    }

    private void sendRespMsg(HttpServerRequest request, String respMsg) {
        HttpServerResponse response = request.response();
        MultiMap headers = response.headers();
        byte[] respByte = respMsg.getBytes();//因为String.length()会将中文字符的长度视为1，因此需要转换成字节数组
        headers.set("Content-Length", String.valueOf(respByte.length));
        headers.set("Content-type", "application/json;charset=utf-8");
        response.write(respMsg);
        response.end();
    }

    @PreDestroy
    public void stop() {
        if (null != httpServer) {
            httpServer.close(res -> {
                if (res.succeeded()) {
                    logger.debug("MatryHttpServer is now closed.");
                } else {
                    logger.debug("MatryHttpServer close fail.");
                }
            });
        }
    }

    private void returnCommonErrorResp(HttpServerRequest request,String encodeType,Integer errorType){
        CommonResp resp = new CommonResp();
        resp.setResult(errorType);
        resp.setMsg(Result.getErrorMsg(errorType));
        sendRespMsg(request, JsonUtil.object2Json(resp));
    }

}
