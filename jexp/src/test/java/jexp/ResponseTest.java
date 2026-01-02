package jexp;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void status() throws NoSuchFieldException, IllegalAccessException {
        Field status = Response.class.getDeclaredField("statusCode");
        status.setAccessible(true);
        Response response = new Response();
        assertEquals(200, status.get(response));
        response.status(201);
        assertEquals(201, status.get(response));
    }

    @Test
    public void header() throws NoSuchFieldException, IllegalAccessException {
        Field headers = Response.class.getDeclaredField("headers");
        headers.setAccessible(true);
        Response response = new Response();
        response.header("header1", "header1Value");
        response.header("header2", "header2Value");
        assertTrue(((Headers) headers.get(response)).containsKey("header1"));
        assertTrue(((Headers) headers.get(response)).containsKey("header2"));
        assertArrayEquals(new String[]{"header1Value"}, ((Headers) headers.get(response)).get("header1").toArray());
        assertArrayEquals(new String[]{"header2Value"}, ((Headers) headers.get(response)).get("header2").toArray());
    }

    @Test
    public void type() throws NoSuchFieldException, IllegalAccessException {
        Field headers = Response.class.getDeclaredField("headers");
        headers.setAccessible(true);
        Response response = new Response();
        response.type("type");
        assertTrue(((Headers) headers.get(response)).containsKey("Content-Type"));
        assertArrayEquals(new String[]{"type"}, ((Headers) headers.get(response)).get("Content-Type").toArray());
    }

    @Test
    public void send() throws Response.ResponseError, NoSuchFieldException, IllegalAccessException {
        Field body = Response.class.getDeclaredField("body");
        body.setAccessible(true);
        Field closed = Response.class.getDeclaredField("closed");
        closed.setAccessible(true);

        Response response = new Response();
        assertNull(body.get(response));
        response.send("body");
        assertEquals("body", body.get(response));
        assertTrue((Boolean) closed.get(response));
        assertThrows(Response.ResponseError.class, () -> {
            response.send("data");
        });
    }

    @Test
    public void end() throws Response.ResponseError, NoSuchFieldException, IllegalAccessException {
        Field closed = Response.class.getDeclaredField("closed");
        closed.setAccessible(true);
        Response response = new Response();
        response.end();
        assertTrue((Boolean) closed.get(response));
        assertThrows(Response.ResponseError.class, () -> {
            response.end();
        });
    }

    @Test
    public void cookie() throws NoSuchFieldException, IllegalAccessException {
        Field headers = Response.class.getDeclaredField("headers");
        headers.setAccessible(true);
        Response response = new Response();
        response.cookie("cookie", "cookieValue", 10, "path");
        assertTrue(((Headers) headers.get(response)).containsKey("Set-Cookie"));
        assertEquals("cookie=cookieValue;Path=path;HttpOnly;Secure=false;Max-Age=10", ((Headers) headers.get(response)).get("Set-Cookie").getFirst());
    }

    @Test
    public void sendResponse() throws Response.ResponseError, IOException, URISyntaxException {
        Response response = new Response();
        response.header("header1", "header1Value");
        String body = "body";
        response.send(body);
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Headers headers = Mockito.mock(Headers.class);
        Mockito.when(exchange.getResponseHeaders()).thenReturn(headers);
        OutputStream bodyStream = Mockito.mock(OutputStream.class);
        Mockito.when(exchange.getResponseBody()).thenReturn(bodyStream);
        response.sendResponse(exchange);
        Mockito.verify(headers, Mockito.times(1)).putAll(Mockito.any());
        Mockito.verify(exchange, Mockito.times(1)).sendResponseHeaders(200, body.length());
        Mockito.verify(bodyStream, Mockito.times(1)).write(Mockito.any());
        Mockito.verify(exchange, Mockito.times(1)).close();
    }

}