package jexp;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class JExpRequestTest {

    @Test
    public void getHeaders() throws URISyntaxException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        exchange.addRequestHeaders("header1", "header1Value");
        exchange.addRequestHeaders("header2", "header2Value");
        JExpRequest request = new JExpRequest(exchange);
        assertSame(exchange.getRequestHeaders(), request.getHeaders());
    }

    @Test
    public void getPath() throws URISyntaxException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        JExpRequest request = new JExpRequest(exchange);
        assertArrayEquals(new String[]{}, request.getPath());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/"));
        request = new JExpRequest(exchange);
        assertArrayEquals(new String[]{"abc"}, request.getPath());
    }

    @Test
    public void getProtocol() throws URISyntaxException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        JExpRequest request = new JExpRequest(exchange);
        assertEquals("Protocol", request.getProtocol());
    }

    @Test
    public void getUrl() throws URISyntaxException {
        TestExchange exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/"));
        JExpRequest request = new JExpRequest(exchange);
        assertEquals("/", request.getUrl());
        exchange = new TestExchange("Protocol", "GET", new URI("http://localhost:8080/abc/"));
        request = new JExpRequest(exchange);
        assertEquals("/abc/", request.getUrl());
    }
}