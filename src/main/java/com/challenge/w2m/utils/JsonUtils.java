package com.challenge.w2m.utils;

import com.challenge.w2m.exception.JsonDeserializationException;
import com.challenge.w2m.exception.JsonSerializationException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonSerializationException("Error serializing object to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonDeserializationException("Error deserializing JSON to object", e);
        }
    }
}
