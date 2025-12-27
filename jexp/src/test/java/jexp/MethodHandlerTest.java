package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MethodHandlerTest {

    @Test
    public void handleRightMethod() throws URISyntaxException, Next.NextError, Response.ResponseError, IOException {
        Handler handler = Mockito.mock(Handler.class);
        MethodHandler methodHandler = new MethodHandler("GET", handler);
        Request request = new Request(new TestExchange());
        Response response = new Response();
        Next next = new Next();
        methodHandler.handle(request, response, next);
        Mockito.verify(handler, Mockito.times(1)).handle(request, response, next);
    }

    @Test
    public void handleWrongMethod() throws URISyntaxException, Next.NextError, Response.ResponseError, IOException {
        Handler handler = Mockito.mock(Handler.class);
        MethodHandler methodHandler = new MethodHandler("GET", handler);
        Request request = new Request(new TestExchange("protocol", "POST", new URI("https://localhost:8080")));
        Response response = new Response();
        Next next = new Next();
        methodHandler.handle(request, response, next);
        Mockito.verify(handler, Mockito.times(0)).handle(request, response, next);
        assertTrue(next.getNext());
        assertFalse(next.getNextRoute());
    }
}