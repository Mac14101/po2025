package jexp.defaultHandlers;

import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class SessionRefreshHandlerTest {

    @Test
    public void withSession() throws JExpError {
        SessionRefreshHandler handler = new SessionRefreshHandler();
        Request request = Mockito.mock(Request.class);

        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.refreshSession()).thenReturn("newToken");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        handler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(1)).refreshSession();
        Mockito.verify(response, Mockito.times(1)).type("application/json");
        Mockito.verify(response, Mockito.times(1)).status(200);
        Mockito.verify(response, Mockito.times(1)).send("newToken");
    }

    @Test
    public void withoutSession() throws JExpError {
        SessionRefreshHandler handler = new SessionRefreshHandler();
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
    }
}