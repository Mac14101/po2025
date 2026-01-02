package jexp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JSONTest {

    @Test
    public void parse() throws JsonProcessingException {
        String json = "{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"}";
        TestJson result = JSON.parse(json, TestJson.class);
        assertEquals(1, result.a);
        assertEquals(2, result.b);
        assertEquals(3, result.c);
        json = "[{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"},{\"a\":\"4\",\"b\":\"5\",\"c\":\"6\"}]";
        ArrayList<TestJson> resultNew = JSON.parse(json, new TypeReference<ArrayList<TestJson>>() {
        });
        assertEquals(1, resultNew.get(0).a);
        assertEquals(2, resultNew.get(0).b);
        assertEquals(3, resultNew.get(0).c);
        assertEquals(4, resultNew.get(1).a);
        assertEquals(5, resultNew.get(1).b);
        assertEquals(6, resultNew.get(1).c);
    }

    @Test
    public void stringify() throws JsonProcessingException {
        TestJson object = new TestJson();
        object.a = 1;
        object.b = 2;
        object.c = 3;
        assertEquals("{\"a\":1,\"b\":2,\"c\":3}", JSON.stringify(object));
    }

    public static class TestJson {
        public int a;
        public int b;
        public int c;
    }
}