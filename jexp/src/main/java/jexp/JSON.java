package jexp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
    private static JSON instance;
    private ObjectMapper mapper;

    private JSON() {
        this.mapper = new ObjectMapper();
    }

    private static JSON getInstance() {
        if (instance == null) {
            instance = new JSON();
        }
        return instance;
    }

    public static <T> T parse(String json, Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.readValue(json, type);
    }

    public static <T> T parse(String json, TypeReference<T> type) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.readValue(json, type);
    }

    public static String stringify(Object object) throws JsonProcessingException {
        ObjectMapper mapper = getInstance().getMapper();
        return mapper.writeValueAsString(object);
    }

    private ObjectMapper getMapper() {
        return this.mapper;
    }
}