package jexp;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import jexp.session.Manager;
import jexp.session.Session;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void getHeaders() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Headers headers = new Headers();
        headers.add("header1", "value1");
        headers.add("header2", "value2");
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080"));
        Request request = new Request(exchange);
        assertSame(headers, request.getHeaders());
    }

    @Test
    public void getRoutePath() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/"));
        Request request = new Request(exchange);
        assertArrayEquals(new String[]{}, request.getRoute());
        exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/abc/abc/"));
        request = new Request(exchange);
        assertArrayEquals(new String[]{"abc", "abc"}, request.getRoute());
        assertEquals("/abc/abc/", request.getPath());
    }

    @Test
    public void getProtocol() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/"));
        Mockito.when(exchange.getProtocol()).thenReturn("Protocol");
        Request request = new Request(exchange);
        assertEquals("Protocol", request.getProtocol());
    }

    @Test
    public void getUrl() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        String url = "https://localhost:8080/";
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI(url));
        Request request = new Request(exchange);
        assertEquals(url, request.getUrl());
        exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        url = "https://localhost:8080/abc/abc/";
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI(url));
        request = new Request(exchange);
        assertEquals(url, request.getUrl());
    }

    @Test
    public void getMethod() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/"));
        Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
        Request request = new Request(exchange);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void getQuery() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/?a=1&b=2"));
        Request request = new Request(exchange);
        assertEquals("1", request.getQuery("a"));
        assertEquals("2", request.getQuery("b"));
        assertNull(request.getQuery("c"));
    }

    @Test
    public void getBody() throws URISyntaxException, Request.RequestError, NoSuchFieldException, IOException, IllegalAccessException {
        Field body = Request.class.getDeclaredField("body");
        body.setAccessible(true);
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
        InputStream bodyStream = Mockito.mock(InputStream.class);
        Mockito.when(exchange.getRequestBody()).thenReturn(bodyStream);
        Mockito.when(bodyStream.readAllBytes()).thenReturn("{a:1}".getBytes());
        Request request = new Request(exchange);
        Mockito.verify(bodyStream, Mockito.times(1)).readAllBytes();
        assertEquals("{a:1}", body.get(request));
    }

    @Test
    public void getCookie() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Headers headers = new Headers();
        headers.add("Cookie", "cookie1=value1;cookie2=value2;");
        Mockito.when(exchange.getRequestHeaders()).thenReturn(headers);
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        assertEquals("value1", request.getCookie("cookie1"));
        assertEquals("value2", request.getCookie("cookie2"));
        assertTrue(request.getCookies().containsKey("cookie1"));
        assertTrue(request.getCookies().containsKey("cookie2"));
    }

    @Test
    public void param() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/a/2/c"));
        Request request = new Request(exchange);
        request.readParam(":id", 1);
        assertEquals("2", request.getParam("id"));
        assertArrayEquals(new String[]{"a", ":id", "c"}, request.getRoute());
    }

    @Test
    public void getSession() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.getSession(Mockito.anyString())).thenReturn("session");
        request.setSessionObject(session);
        assertEquals("session", request.getSession("key"));
        Mockito.verify(session, Mockito.times(1)).getSession("key");
    }

    @Test
    public void setSession() throws URISyntaxException, Request.RequestError {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
        Request request = new Request(exchange);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.getSession(Mockito.anyString())).thenReturn("session");
        request.setSessionObject(session);
        request.setSession("key", "session");
        Mockito.verify(session, Mockito.times(1)).setSession("key", "session");
    }

    @Test
    public void logIn() throws URISyntaxException, Request.RequestError {
        try (MockedStatic<Manager> managerMockedStatic = Mockito.mockStatic(Manager.class)) {
            Manager manager = Mockito.mock(Manager.class);
            managerMockedStatic.when(Manager::getInstance).thenReturn(manager);
            Mockito.when(manager.addSession()).thenReturn("token");
            Session session = Mockito.mock(Session.class);
            Mockito.when(manager.getSession(Mockito.anyString())).thenReturn(session);
            HttpExchange exchange = Mockito.mock(HttpExchange.class);
            Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
            Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
            Request request = new Request(exchange);
            assertEquals("token", request.logIn(1, "username"));
            Mockito.verify(manager, Mockito.times(1)).addSession();
            Mockito.verify(session).setSession("userId", "1");
            Mockito.verify(session).setSession("username", "username");
        }
    }

    @Test
    public void logOut() throws URISyntaxException, Request.RequestError, NoSuchFieldException, IllegalAccessException {
        try (MockedStatic<Manager> managerMockedStatic = Mockito.mockStatic(Manager.class)) {
            Field sessionField = Request.class.getDeclaredField("session");
            sessionField.setAccessible(true);
            Manager manager = Mockito.mock(Manager.class);
            managerMockedStatic.when(Manager::getInstance).thenReturn(manager);
            HttpExchange exchange = Mockito.mock(HttpExchange.class);
            Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
            Mockito.when(exchange.getRequestURI()).thenReturn(new URI("http://localhost:8080/"));
            Request request = new Request(exchange);
            Session session = Mockito.mock(Session.class);
            Mockito.when(session.getToken()).thenReturn("token");
            request.setSessionObject(session);
            request.logOut();
            Mockito.verify(manager, Mockito.times(1)).removeSession("token");
            assertNull(sessionField.get(request));
        }
    }
}