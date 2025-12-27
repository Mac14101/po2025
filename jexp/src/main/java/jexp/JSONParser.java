package jexp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser extends ObjectMapper {
    private static JSONParser mapper;

    private JSONParser() {
    }

    public static <T> T parse(String json, Class<T> type) throws JsonProcessingException {
        if (mapper == null) {
            mapper = new JSONParser();
        }
        return mapper.readValue(json, type);
    }

    public static <T> T parse(String json, TypeReference<T> type) throws JsonProcessingException {
        if (mapper == null) {
            mapper = new JSONParser();
        }
        return mapper.readValue(json, type);
    }
}