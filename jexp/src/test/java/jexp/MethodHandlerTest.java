package jexp;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MethodHandlerTest {

    @Test
    public void handleRightMethod() throws URISyntaxException, JExpError {
        Handler handler = Mockito.mock(Handler.class);
        MethodHandler methodHandler = new MethodHandler("GET", handler);
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/"));
        Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
        Request request = new Request(exchange);
        Response response = new Response();
        Next next = new Next();
        methodHandler.handle(request, response, next);
        Mockito.verify(handler, Mockito.times(1)).handle(request, response, next);
    }

    @Test
    public void handleWrongMethod() throws URISyntaxException, JExpError {
        Handler handler = Mockito.mock(Handler.class);
        MethodHandler methodHandler = new MethodHandler("GET", handler);
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestHeaders()).thenReturn(new Headers());
        Mockito.when(exchange.getRequestURI()).thenReturn(new URI("https://localhost:8080/"));
        Mockito.when(exchange.getRequestMethod()).thenReturn("POST");
        Request request = new Request(exchange);
        Response response = new Response();
        Next next = new Next();
        methodHandler.handle(request, response, next);
        Mockito.verify(handler, Mockito.times(0)).handle(request, response, next);
        assertTrue(next.getNext());
        assertFalse(next.getNextRoute());
    }
}