package jexp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
    private static ObjectMapper mapper;

    private JSON() {
    }

    public static <T> T parse(String json, Class<T> type) throws JsonProcessingException {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper.readValue(json, type);
    }

    public static <T> T parse(String json, TypeReference<T> type) throws JsonProcessingException {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper.readValue(json, type);
    }
}