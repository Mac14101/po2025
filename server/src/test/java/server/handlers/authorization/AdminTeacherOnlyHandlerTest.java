package server.handlers.authorization;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminTeacherOnlyHandlerTest {

    @Test
    public void userAdmin() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.ADMIN.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminTeacherOnlyHandler handler = new AdminTeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }

    @Test
    public void userTeacher() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.TEACHER.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminTeacherOnlyHandler handler = new AdminTeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }

    @Test
    public void userStudent() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.STUDENT.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AdminTeacherOnlyHandler handler = new AdminTeacherOnlyHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
    }

}