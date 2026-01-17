package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class UserDataHandlerTest {

    @Test
    public void withSession() throws NoSuchFieldException, JExpError, IllegalAccessException {
        Field database = UserDataHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        User user = Mockito.mock(User.class);
        Mockito.when(applicationDatabase.getUserData(Mockito.anyInt())).thenReturn(user);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Mockito.when(request.getSession("userId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        UserDataHandler userDataHandler = new UserDataHandler();
        database.set(userDataHandler, applicationDatabase);
        userDataHandler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getUserData(Mockito.anyInt());
        Mockito.verify(response, Mockito.times(1)).json(user);
    }

    @Test
    public void withoutSession() throws NoSuchFieldException, JExpError, IllegalAccessException {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        UserDataHandler userDataHandler = new UserDataHandler();
        userDataHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(401);
        Mockito.verify(response, Mockito.times(1)).end();
        Mockito.verify(response, Mockito.never()).json(Mockito.anyString());
    }
}