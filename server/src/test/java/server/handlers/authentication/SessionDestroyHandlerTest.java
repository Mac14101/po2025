package server.handlers.authentication;

import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class SessionDestroyHandlerTest {

    @Test
    public void withSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        SessionDestroyHandler handler = new SessionDestroyHandler();
        handler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(1)).logOut();
        Mockito.verify(response, Mockito.times(1)).status(200);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void withoutSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        SessionDestroyHandler handler = new SessionDestroyHandler();
        handler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(0)).logOut();
        Mockito.verify(response, Mockito.times(1)).status(200);
        Mockito.verify(response, Mockito.times(1)).end();
    }
}