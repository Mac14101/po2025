package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RouterTest {

    @Test
    public void depth() {
        Router router = new Router();
        assertEquals(0, router.getDepth());
        router.setDepth(2);
        assertEquals(2, router.getDepth());
    }

    @Test
    public void use() throws Route.RouteError {
        Handler handler1 = Mockito.mock(Handler.class);
        Router router = new Router();
        router.use("/", handler1);
        assertSame(handler1, router.getRoutingList().getHandlers("").getFirst());
        Handler handler2 = Mockito.mock(Handler.class);
        router.use("/", handler2);
        assertSame(handler1, router.getRoutingList().getHandlers("").getFirst());
        assertSame(handler2, router.getRoutingList().getHandlers("").get(1));
        Handler handler3 = Mockito.mock(Handler.class);
        router.use("/a/", handler3);
        assertSame(handler3, router.getRoutingList().getHandlers("a").getFirst());
        Handler handler4 = Mockito.mock(Handler.class);
        router.use("/a/b/", handler4);
        assertSame(handler3, router.getRoutingList().getHandlers("a").getFirst());
        assertTrue(router.getRoutingList().getHandlers("a").get(1) instanceof Router);
        assertSame(handler4, ((Router) router.getRoutingList().getHandlers("a").get(1)).getRoutingList().getHandlers("b").getFirst());

        Router routerNew = new Router();
        assertEquals(0, routerNew.getDepth());
        router.use("/c/", routerNew);
        assertSame(1, ((Router) router.getRoutingList().getHandlers("c").getFirst()).getDepth());
    }

    @Test
    public void handle() throws URISyntaxException, Next.NextError, Response.ResponseError, Route.RouteError, IOException {
        TestExchange exchange = new TestExchange("protocol", "GET", new URI("http://localhost:8080/"));
        Request request;
        Response response;
        Next next;
        Router router = new Router();
        request = new Request(exchange);
        response = new Response();
        next = new Next();
        router.handle(request, response, next);
        assertTrue(next.getNext());
        assertFalse(next.getNextRoute());
        Handler handler1 = Mockito.mock(Handler.class);
        Handler handler2 = Mockito.mock(Handler.class);
        router.use("/", handler1);
        router.use("/", handler2);
        request = new Request(exchange);
        response = new Response();
        next = new Next();
        router.handle(request, response, next);
        assertFalse(next.getNext());
        assertFalse(next.getNextRoute());
        verify(handler1, times(1)).handle(any(Request.class), any(Response.class), any(Next.class));
        verify(handler2, times(0)).handle(any(Request.class), any(Response.class), any(Next.class));
        doAnswer(inv -> {
            Request req = inv.getArgument(0);
            Response res = inv.getArgument(1);
            Next n = inv.getArgument(2);
            n.next();
            return null;
        }).when(handler1).handle(any(Request.class), any(Response.class), any(Next.class));
        request = new Request(exchange);
        response = new Response();
        next = new Next();
        router.handle(request, response, next);
        assertFalse(next.getNext());
        assertFalse(next.getNextRoute());
        verify(handler1, times(2)).handle(any(Request.class), any(Response.class), any(Next.class));
        verify(handler2, times(1)).handle(any(Request.class), any(Response.class), any(Next.class));
        doAnswer(inv -> {
            Request req = inv.getArgument(0);
            Response res = inv.getArgument(1);
            Next n = inv.getArgument(2);
            n.nextRoute();
            return null;
        }).when(handler1).handle(any(Request.class), any(Response.class), any(Next.class));
        request = new Request(exchange);
        response = new Response();
        next = new Next();
        router.handle(request, response, next);
        assertTrue(next.getNext());
        assertFalse(next.getNextRoute());
        verify(handler1, times(3)).handle(any(Request.class), any(Response.class), any(Next.class));
        verify(handler2, times(1)).handle(any(Request.class), any(Response.class), any(Next.class));
    }

}