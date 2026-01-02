package jexp;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void getHeaders() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        exchange.addRequestHeaders("header1", "header1Value");
        exchange.addRequestHeaders("header2", "header2Value");
        Request request = new Request(exchange);
        assertSame(exchange.getRequestHeaders(), request.getHeaders());
    }

    @Test
    public void getRoutePath() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertArrayEquals(new String[]{}, request.getRoute());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertArrayEquals(new String[]{"abc", "abc"}, request.getRoute());
        assertEquals("/abc/abc/", request.getPath());
    }

    @Test
    public void getProtocol() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("Protocol", request.getProtocol());
    }

    @Test
    public void getUrl() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("http://localhost:8080/", request.getUrl());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertEquals("http://localhost:8080/abc/abc/", request.getUrl());
    }

    @Test
    public void getMethod() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void getQuery() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/?a=1&b=2"));
        Request request = new Request(exchange);
        assertEquals("1", request.getQuery("a"));
        assertEquals("2", request.getQuery("b"));
        assertNull(request.getQuery("c"));
    }

    @Test
    public void param() throws URISyntaxException, Request.RequestError {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/a/2/c"));
        Request request = new Request(exchange);
        request.readParam(":id", 1);
        assertEquals("2", request.getParam("id"));
        assertTrue(request.getParams().containsKey(":id"));
        assertArrayEquals(new String[]{"a", ":id", "c"}, request.getRoute());
    }
}