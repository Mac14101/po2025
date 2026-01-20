package server.handlers.authorization;

import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthenticatedUsersOnlyHandlerTest {

    @Test
    public void withSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticatedUsersOnlyHandler authenticatedUsersOnlyHandler = new AuthenticatedUsersOnlyHandler();
        authenticatedUsersOnlyHandler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }

    @Test
    public void withoutSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticatedUsersOnlyHandler authenticatedUsersOnlyHandler = new AuthenticatedUsersOnlyHandler();
        authenticatedUsersOnlyHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }
}