package jexp;

import com.sun.net.httpserver.Headers;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void status() {
        Response response = new Response();
        assertEquals(200, response.getStatusCode());
        response.status(201);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void header() {
        Response response = new Response();
        response.header("header1", "header1Value");
        response.header("header2", "header2Value");
        assertTrue(response.getHeaders().containsKey("header1"));
        assertTrue(response.getHeaders().containsKey("header2"));
        assertArrayEquals(new String[]{"header1Value"}, response.getHeaders().get("header1").toArray());
        assertArrayEquals(new String[]{"header2Value"}, response.getHeaders().get("header2").toArray());
        Headers headers = new Headers();
        headers.add("header1", "header1Value");
        headers.add("header2", "header2Value");
        response = new Response(headers);
        assertTrue(response.getHeaders().containsKey("header1"));
        assertTrue(response.getHeaders().containsKey("header2"));
        assertArrayEquals(new String[]{"header1Value"}, response.getHeaders().get("header1").toArray());
        assertArrayEquals(new String[]{"header2Value"}, response.getHeaders().get("header2").toArray());
    }

    @Test
    public void type() {
        Response response = new Response();
        response.type("type");
        assertTrue(response.getHeaders().containsKey("Content-Type"));
        assertArrayEquals(new String[]{"type"}, response.getHeaders().get("Content-Type").toArray());
    }

    @Test
    public void send() throws Exception {
        Response response = new Response();
        assertNull(response.getBody());
        response.send("body");
        assertEquals("body", response.getBody());
        assertTrue(response.isClosed());
        assertThrows(Exception.class, () -> {
            response.send("data");
        });
    }

    @Test
    public void end() throws Exception {
        Response response = new Response();
        response.end();
        assertTrue(response.isClosed());
        assertThrows(Exception.class, () -> {
            response.end();
        });
    }
}