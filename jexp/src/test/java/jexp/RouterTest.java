package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RouterTest {
    @Test
    public void testUse() throws NoSuchFieldException, IllegalAccessException {
        Router router = new Router();
        Field handlers = Router.class.getDeclaredField("handlers");
        handlers.setAccessible(true);
        Handler handler1 = Mockito.mock(Handler.class);
        Handler handler2 = Mockito.mock(Handler.class);
        Handler handler3 = Mockito.mock(Handler.class);
        Handler handler4 = Mockito.mock(Handler.class);
        Handler handler5 = Mockito.mock(Handler.class);
        Handler handler6 = Mockito.mock(Handler.class);
        router.use("/", handler1);
        router.use("/a/", handler2);
        router.use("/a/b", handler3);
        router.use("/b/a/", handler4);
        router.use("/b/a/", handler5);
        router.use("/:c/a", handler6);
        ArrayList<Handler> mainRouterHandlers = (ArrayList<Handler>) handlers.get(router);
        ArrayList<Handler> aRouterHandlers = (ArrayList<Handler>) handlers.get(mainRouterHandlers.get(1));
        ArrayList<Handler> abRouterHandlers = (ArrayList<Handler>) handlers.get(aRouterHandlers.get(1));
        ArrayList<Handler> bRouterHandlers = (ArrayList<Handler>) handlers.get(mainRouterHandlers.get(2));
        ArrayList<Handler> baRouterHandlers = (ArrayList<Handler>) handlers.get(bRouterHandlers.get(0));
        ParamHandler paramHandler = (ParamHandler) mainRouterHandlers.get(3);
        ArrayList<Handler> cRouterHandlers = (ArrayList<Handler>) handlers.get(mainRouterHandlers.get(4));
        ArrayList<Handler> caRouterHandlers = (ArrayList<Handler>) handlers.get(cRouterHandlers.get(0));
        assertSame(mainRouterHandlers.get(0), handler1);
        assertSame(aRouterHandlers.get(0), handler2);
        assertSame(abRouterHandlers.get(0), handler3);
        assertSame(baRouterHandlers.get(0), handler4);
        assertSame(baRouterHandlers.get(1), handler5);
        assertTrue(paramHandler instanceof ParamHandler);
        assertSame(caRouterHandlers.get(0), handler6);
    }

    @Test
    public void testGet() throws NoSuchFieldException, IllegalAccessException {
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
    public void testPost() throws NoSuchFieldException, IllegalAccessException {
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
    public void testPut() throws NoSuchFieldException, IllegalAccessException {
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
    public void testDelete() throws NoSuchFieldException, IllegalAccessException {
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
    public void testHandle() throws JExpError {
        Router router = new Router();
        Handler handler1 = Mockito.spy(new Handler() {
            @Override
            public void handle(Request request, Response response, Next next) throws JExpError {
                next.next();
            }
        });
        Handler handler2 = Mockito.spy(new Handler() {
            @Override
            public void handle(Request request, Response response, Next next) throws JExpError {
                next.next();
            }
        });
        Handler handler3 = Mockito.spy(new Handler() {
            @Override
            public void handle(Request request, Response response, Next next) throws JExpError {
                next.next();
            }
        });
        Handler handler4 = Mockito.spy(new Handler() {
            @Override
            public void handle(Request request, Response response, Next next) throws JExpError {
            }
        });
        Handler handler5 = Mockito.spy(new Handler() {
            @Override
            public void handle(Request request, Response response, Next next) throws JExpError {
                next.next();
            }
        });
        router.use("/", handler1);
        router.use("/a/", handler2);
        router.use("/b/", handler3);
        router.use("/b/", handler4);
        router.use("/b/", handler5);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getRoute()).thenReturn(new String[]{"b"});
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        router.handle(request, response, next);
        Mockito.verify(handler1, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler2, Mockito.times(0)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler3, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler4, Mockito.times(1)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
        Mockito.verify(handler5, Mockito.times(0)).handle(Mockito.eq(request), Mockito.eq(response), Mockito.any(Next.class));
    }
}