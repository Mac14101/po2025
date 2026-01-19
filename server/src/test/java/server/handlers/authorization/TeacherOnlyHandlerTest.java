package server.handlers.authorization;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class TeacherOnlyHandlerTest {

    @Test
    public void withoutSession() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherOnlyHandler handler = new TeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void notTeacher() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.STUDENT.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherOnlyHandler handler = new TeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

    @Test
    public void teacher() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.TEACHER.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherOnlyHandler handler = new TeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.never()).status(401);
        Mockito.verify(response, Mockito.never()).end();
        Mockito.verify(next, Mockito.times(1)).next();
    }
}