package com.matry.vertx.util;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JsonUtil {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }


    public static String object2Json(Object o) {
        if (null == o) {
            return null;
        } else {
            try {
                return  objectMapper.writeValueAsString(o);
            } catch (Exception e) {
                logger.error("objectToJson:{} failure.exception:{}", o, ExceptionUtils.getStackTrace(e));
                return null;
            }
        }
    }

    public static <T> T jsonTObject(String json, Class<T> type) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            } else {
                return objectMapper.readValue(json, type);

            }
        } catch (Exception e) {
            logger.error("json:{} to object error.exception:", json, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static <T> T jsonTObject(byte[] src, TypeReference type) {
        try {
            if (StringUtils.isEmpty(src)) {
                return null;
            } else {
                return (T) objectMapper.readValue(src, type);
            }
        } catch (Exception e) {
            logger.error("byte[]:{} to object error.exception:", src, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
