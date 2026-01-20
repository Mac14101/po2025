package server.handlers.authorization;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class StudentOnlyHandlerTest {

    @Test
    public void notStudent() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.ADMIN.toString().toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        StudentOnlyHandler handler = new StudentOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void student() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.STUDENT.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        StudentOnlyHandler handler = new StudentOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.never()).status(401);
        Mockito.verify(response, Mockito.never()).end();
        Mockito.verify(next, Mockito.times(1)).next();
    }
}