package jexp;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void getHeaders() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        exchange.addRequestHeaders("header1", "header1Value");
        exchange.addRequestHeaders("header2", "header2Value");
        Request request = new Request(exchange);
        assertSame(exchange.getRequestHeaders(), request.getHeaders());
    }

    @Test
    public void getRoute() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertArrayEquals(new String[]{}, request.getRoute());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertArrayEquals(new String[]{"abc", "abc"}, request.getRoute());
    }

    @Test
    public void getPath() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("/", request.getPath());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertEquals("/abc/abc/", request.getPath());
    }

    @Test
    public void getProtocol() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("Protocol", request.getProtocol());
    }

    @Test
    public void getUrl() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("http://localhost:8080/", request.getUrl());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertEquals("http://localhost:8080/abc/abc/", request.getUrl());
    }

    @Test
    public void getMethod() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void getQuery() throws URISyntaxException, IOException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/?a=1&b=2"));
        Request request = new Request(exchange);
        assertEquals("1", request.getQuery("a"));
        assertEquals("2", request.getQuery("b"));
        assertNull(request.getQuery("c"));
    }

}