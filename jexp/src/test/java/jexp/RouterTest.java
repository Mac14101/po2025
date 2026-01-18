package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RouterTest {
    @Test
    public void use() throws NoSuchFieldException, IllegalAccessException, Router.RouterError {
        Router router = new Router();
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Field route = Router.class.getDeclaredField("route");
        route.setAccessible(true);
        Handler handler1 = Mockito.mock(Handler.class);
        Handler handler2 = Mockito.mock(Handler.class);
        Router handler3 = new Router();
        Handler handler4 = Mockito.mock(Handler.class);
        Handler handler5 = Mockito.mock(Handler.class);
        Handler handler6 = Mockito.mock(Handler.class);
        router.use("/", handler1);
        router.use("/", handler2);
        router.use("/a/", handler3);
        router.use("/x/y/", handler4);
        router.use("/x/y/", handler5);
        router.use("/:p/", handler6);
        ArrayList<Handler> mainRouterHandlers = (ArrayList<Handler>) handlers.get(router);
        ArrayList<Handler> xRouterHandlers = (ArrayList<Handler>) handlers.get(((ArrayList<Handler>) handlers.get(router)).get(3));
        ArrayList<Handler> xyRouterHandlers = (ArrayList<Handler>) handlers.get(xRouterHandlers.get(0));
        ArrayList<Handler> pRouterHandlers = (ArrayList<Handler>) handlers.get(mainRouterHandlers.get(5));
        assertSame(handler1, mainRouterHandlers.get(0));
        assertSame(handler2, mainRouterHandlers.get(1));
        assertSame(handler3, mainRouterHandlers.get(2));
        assertEquals("a", route.get(handler3));
        assertSame(handler4, xyRouterHandlers.get(0));
        assertSame(handler5, xyRouterHandlers.get(1));
        assertTrue(mainRouterHandlers.get(4) instanceof ParamHandler);
        assertSame(handler6, pRouterHandlers.get(0));
    }

    @Test
    public void updateDepth() throws Router.RouterError {
        Router router = new Router();
        Router router1 = Mockito.mock(Router.class);
        Router router2 = Mockito.mock(Router.class);
        ParamHandler paramHandler = Mockito.mock(ParamHandler.class);
        router.use("/", router1);
        router.use("/", router2);
        router.use("/", paramHandler);
        router.updateDepth();
        Mockito.verify(router1, Mockito.times(1)).setDepth(Mockito.anyInt());
        Mockito.verify(router1, Mockito.times(1)).updateDepth();
        Mockito.verify(router2, Mockito.times(1)).setDepth(Mockito.anyInt());
        Mockito.verify(router2, Mockito.times(1)).updateDepth();
        Mockito.verify(paramHandler, Mockito.times(1)).setDepth(Mockito.anyInt());
    }


    @Test
    public void get() throws NoSuchFieldException, IllegalAccessException, Router.RouterError {
        Router router = Mockito.spy(new Router());
        Handler handler = Mockito.mock(Handler.class);
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Field method = MethodHandler.class.getDeclaredField("method");
        method.setAccessible(true);
        router.get("/", handler);
        Mockito.verify(router, Mockito.times(1)).use(Mockito.eq("/"), Mockito.any(MethodHandler.class));
        assertEquals("get", method.get(((ArrayList<Handler>) handlers.get(router)).getFirst()));
    }

    @Test
    public void post() throws NoSuchFieldException, IllegalAccessException, Router.RouterError {
        Router router = Mockito.spy(new Router());
        Handler handler = Mockito.mock(Handler.class);
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Field method = MethodHandler.class.getDeclaredField("method");
        method.setAccessible(true);
        router.post("/", handler);
        Mockito.verify(router, Mockito.times(1)).use(Mockito.eq("/"), Mockito.any(MethodHandler.class));
        assertEquals("post", method.get(((ArrayList<Handler>) handlers.get(router)).getFirst()));
    }

    @Test
    public void put() throws NoSuchFieldException, IllegalAccessException, Router.RouterError {
        Router router = Mockito.spy(new Router());
        Handler handler = Mockito.mock(Handler.class);
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Field method = MethodHandler.class.getDeclaredField("method");
        method.setAccessible(true);
        router.put("/", handler);
        Mockito.verify(router, Mockito.times(1)).use(Mockito.eq("/"), Mockito.any(MethodHandler.class));
        assertEquals("put", method.get(((ArrayList<Handler>) handlers.get(router)).getFirst()));
    }

    @Test
    public void delete() throws NoSuchFieldException, IllegalAccessException, Router.RouterError {
        Router router = Mockito.spy(new Router());
        Handler handler = Mockito.mock(Handler.class);
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Field method = MethodHandler.class.getDeclaredField("method");
        method.setAccessible(true);
        router.delete("/", handler);
        Mockito.verify(router, Mockito.times(1)).use(Mockito.eq("/"), Mockito.any(MethodHandler.class));
        assertEquals("delete", method.get(((ArrayList<Handler>) handlers.get(router)).getFirst()));
    }

    @Test
    public void handle() throws JExpError {
        Router router = Mockito.spy(new Router());
        Handler handler1 = Mockito.mock(Handler.class);
        Handler handler2 = Mockito.mock(Handler.class);
        Handler handler3 = Mockito.mock(Handler.class);
        Handler handler4 = Mockito.mock(Handler.class);
        Handler handler5 = Mockito.mock(Handler.class);
        Handler handler6 = Mockito.mock(Handler.class);
        router.use("/", handler1);
        router.use("/c", handler2);
        router.use("/a/b/", handler3);
        router.use("/a/b/", handler4);
        router.use("/a/", handler5);
        router.use("/a/", handler6);
        router.updateDepth();
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getRoute()).thenReturn(new String[]{"a", "b"});
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        Mockito.doAnswer(invocation -> {
            Next nextH = invocation.getArgument(2);
            nextH.next();
            return null;
        }).when(handler1).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.doAnswer(invocation -> {
            Next nextH = invocation.getArgument(2);
            nextH.nextRoute();
            return null;
        }).when(handler3).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        router.handle(request, response, next);
        Mockito.verify(handler1, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler2, Mockito.times(0)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler3, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler4, Mockito.times(0)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler5, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler6, Mockito.times(0)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
    }
}