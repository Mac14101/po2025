package jexp;

import jexp.defaultHandlers.NotFoundHandler;
import jexp.defaultHandlers.ServerErrorHandler;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultHandlersTest {
    @Test
    public void notFound() throws JExpError {
        NotFoundHandler handler = new NotFoundHandler();
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(404);
        Mockito.verify(response, Mockito.times(1)).send(Mockito.anyString());
        Mockito.verify(next, Mockito.never()).next();
        Mockito.verify(next, Mockito.never()).nextRoute();
    }

    @Test
    public void serverError() throws JExpError {
        ServerErrorHandler handler = new ServerErrorHandler();
        Exception exception = Mockito.mock(Exception.class);
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        handler.handle(exception, request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(500);
        Mockito.verify(response, Mockito.times(1)).send(Mockito.anyString());
        Mockito.verify(next, Mockito.never()).next();
        Mockito.verify(next, Mockito.never()).nextRoute();
    }
}
