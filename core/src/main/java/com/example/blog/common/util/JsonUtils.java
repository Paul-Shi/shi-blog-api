package com.example.blog.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private final static ObjectMapper objMapper = new ObjectMapper();

    public static <T> T toObj(String jsonString, Class<T> clazz) {
        objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("Json字符串转化成对象出错", e);
        }
        return null;
    }

    public static String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        try {
            return objMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("转化成Json字符串", e);
        }
        return null;
    }
}
