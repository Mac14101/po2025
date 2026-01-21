package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.Message;
import entities.UserChangePassword;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;

public class UpdateUserPasswordHandlerTest {

    @Test
    public void withoutPassword() throws JExpError {
        Request request = Mockito.mock(Request.class);
        UserChangePassword userChangePassword = new UserChangePassword();
        Mockito.when(request.getBody(UserChangePassword.class)).thenReturn(userChangePassword);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        UpdateUserPasswordHandler handler = new UpdateUserPasswordHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(400);
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(Message.class));
    }

    @Test
    public void wrongPassword() throws JExpError {
        Request request = Mockito.mock(Request.class);
        UserChangePassword userChangePassword = new UserChangePassword();
        userChangePassword.newPassword = "password";
        userChangePassword.newPasswordConfirm = "password1";
        Mockito.when(request.getBody(UserChangePassword.class)).thenReturn(userChangePassword);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        UpdateUserPasswordHandler handler = new UpdateUserPasswordHandler();
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(400);
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(Message.class));
    }

    @Test
    public void correctPassword() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        UserChangePassword userChangePassword = new UserChangePassword();
        userChangePassword.newPassword = "password";
        userChangePassword.newPasswordConfirm = "password";
        Mockito.when(request.getBody(UserChangePassword.class)).thenReturn(userChangePassword);
        Mockito.when(request.getSession("userId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        UpdateUserPasswordHandler handler = new UpdateUserPasswordHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).updateUserPassword(1, "password");
    }
}