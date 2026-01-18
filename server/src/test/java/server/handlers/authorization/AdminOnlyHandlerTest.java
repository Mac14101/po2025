package server.handlers.authorization;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminOnlyHandlerTest {

    @Test
    public void withoutSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminOnlyHandler adminOnlyHandler = new AdminOnlyHandler();
        adminOnlyHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void notAdmin() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.STUDENT.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminOnlyHandler adminOnlyHandler = new AdminOnlyHandler();
        adminOnlyHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void admin() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.ADMIN.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminOnlyHandler adminOnlyHandler = new AdminOnlyHandler();
        adminOnlyHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.never()).status(401);
        Mockito.verify(response, Mockito.never()).end();
        Mockito.verify(next, Mockito.times(1)).next();
    }
}